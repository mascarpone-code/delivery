package com.mascarpone.delivery.writeandread;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import com.mascarpone.delivery.service.nomenclatureunit.NomenclatureUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class WritePdfNomenclature {
    private final NomenclatureUnitService nomenclatureUnitService;

    public byte[] nomenclatureReport(ShopBranch shopBranch, List<Nomenclature> nomenclatureList) {

        Document document = new Document(PageSize.A4); // создали документ
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open(); //открыли документ для работы

            BaseFont font1 = BaseFont.createFont("/Users/Developer/Downloads/Roboto/Roboto-Bold.ttf", "cp1251", BaseFont.EMBEDDED);
            BaseFont font2 = BaseFont.createFont("/Users/Developer/Downloads/Roboto/Roboto-Medium.ttf", "cp1251", BaseFont.EMBEDDED);
            BaseFont font3 = BaseFont.createFont("/Users/Developer/Downloads/Roboto/Roboto-Regular.ttf", "cp1251", BaseFont.EMBEDDED);
            Font fontRobotoRegular = new Font(font3, 14);

            Paragraph p = new Paragraph("Лист заказа", new Font(font1, 16));
            p.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p);


            Font fontRobotoMedium = new Font(font2, 14);
            Paragraph pDate = new Paragraph("Дата заказа: " + LocalDate.now(), fontRobotoRegular);
            pDate.setSpacingBefore(10);
            document.add(pDate);

            Paragraph p2 = new Paragraph("Заказчик: " + shopBranch.getShop().getName(), fontRobotoMedium);
            p2.setSpacingBefore(10);
            document.add(p2);
            writeContactInformation(document, shopBranch);

            PdfPTable table = new PdfPTable(3);//создаем таблицу
            table.setWidthPercentage(100f);// ширина таблицы - 100%
            table.setWidths(new float[]{4f, 1f, 1f}); //соотношение ширины колонок
            table.setSpacingBefore(10); //интервал между таблицей и предыдущим элементом

            writeTableHeader(table); //записываем шапку таблицы
            writeTableData(table, nomenclatureList); //записываем данные в таблицу

            document.add(table); //добавили таблицу в документ
            document.close(); //закроем документ

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();//данные возвращаются как byte []
    }

    private void writeContactInformation(Document document, ShopBranch shopBranch) throws DocumentException, IOException {
        BaseFont font3 = BaseFont.createFont("/Users/Developer/Downloads/Roboto/Roboto-Regular.ttf", "cp1251", BaseFont.EMBEDDED);
        Font fontRobotoRegular = new Font(font3, 14);

        document.add(new Paragraph("Адрес доставки: " + shopBranch.getDeliveryAddress(), fontRobotoRegular));
        document.add(new Paragraph("Телефон: " + shopBranch.getPhoneNumber(), fontRobotoRegular));
        document.add(new Paragraph("Email: " + shopBranch.getEmail(), fontRobotoRegular));
        document.add(new Paragraph("ИНН: " + shopBranch.getItn().toString(), fontRobotoRegular));
        document.add(new Paragraph("КПП: " + shopBranch.getIec().toString(), fontRobotoRegular));
        document.add(new Paragraph("ОГРН: " + shopBranch.getPsrn().toString(), fontRobotoRegular));
    }

    private static void writeTableHeader(PdfPTable table) throws IOException, DocumentException {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        BaseFont font2 = BaseFont.createFont("/Users/Developer/Downloads/Roboto/Roboto-Medium.ttf", "cp1251", BaseFont.EMBEDDED);
        Font fontRobotoMedium = new Font(font2, 14);

        cell.setPhrase(new Phrase("Наименование", fontRobotoMedium));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Кол-во", fontRobotoMedium));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Ед. изм.", fontRobotoMedium));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table, List<Nomenclature> nomenclatureList) throws IOException, DocumentException {
        for (Nomenclature nomenclature : nomenclatureList) {
            if (nomenclature != null) {
                BaseFont font3 = BaseFont.createFont("/Users/Developer/Downloads/Roboto/Roboto-Regular.ttf", "cp1251", BaseFont.EMBEDDED);
                Font fontRobotoRegular = new Font(font3, 12);

                table.addCell(new PdfPCell(new Phrase(nomenclature.getName() != null ? nomenclature.getName() : " ", fontRobotoRegular)));
                table.addCell(new PdfPCell(new Phrase(nomenclature.getQuantityForOrder() != null ? nomenclature.getQuantityForOrder().toString() : " ", fontRobotoRegular)));
                table.addCell(new PdfPCell(new Phrase(nomenclature.getNomenclatureUnit() != null ? nomenclatureUnitService.getOne(nomenclature.getNomenclatureUnit().getId()).getName() : " ", fontRobotoRegular)));
            }
        }
    }
}
