package com.mascarpone.delivery.writeandread.readexcel;

import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import com.mascarpone.delivery.entity.nomenclatureunit.NomenclatureUnit;
import com.mascarpone.delivery.service.nomenclature.NomenclatureService;
import com.mascarpone.delivery.service.nomenclatureunit.NomenclatureUnitService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReadExcel {
    private final NomenclatureService nomenclatureService;
    private final NomenclatureUnitService nomenclatureUnitService;

    public void createOrUpdateNomenclatureAfterReadExcelFile(String path, int numberColumnName, int numberColumnQuantity, int numberColumnUnit, int countHeaderString) {
        try {
            List<List<String>> listAfterReadFromExcel = new ArrayList<>(createOrUpdateNomenclatureAfterReadExcelFile(path).values());

            for (int a = 0; a < countHeaderString; a++) {
                listAfterReadFromExcel.remove(0);
            }

            for (List<String> i : listAfterReadFromExcel) {
                Optional<Nomenclature> optional = nomenclatureService.getByName(i.get(numberColumnName - 1));

                if (optional.isPresent()) { // если позиция номенклатуры с таким именем существует
                    Nomenclature nomenclature = optional.get();
                    nomenclature.setQuantity(Double.parseDouble(i.get(numberColumnQuantity - 1)));//установим заданное количество
                    //  nomenclatureService.save(nomenclature); //изменим позицию
                } else { // если позиция номенклатуры с таким именем отсутствует
                    Nomenclature nomenclature = new Nomenclature();
                    nomenclature.setName(i.get(numberColumnName - 1)); //установим имя
                    nomenclature.setQuantity(Double.parseDouble(i.get(numberColumnQuantity - 1)));//установим количество

                    Optional<NomenclatureUnit> optionalUnitNomenclature = nomenclatureUnitService.getByName(i.get(numberColumnUnit - 1));

                    if (optionalUnitNomenclature.isPresent()) { //если такая единица измерения существует
                        nomenclature.setNomenclatureUnit(optionalUnitNomenclature.get()); //установим id ед.изм.
                    } else {//если такая единица измерения отсутствует
                        NomenclatureUnit unitNomenclature = new NomenclatureUnit();
                        unitNomenclature.setName(i.get(numberColumnUnit - 1));
                        nomenclatureUnitService.save(unitNomenclature); //создаем ед.изм. и сохраняем ее
                        nomenclature.setNomenclatureUnit(unitNomenclature);//задаем номенклатурной позиции id ед.изм.
                    }

                    nomenclatureService.save(nomenclature); //сохраним созданный объект позиции номенклатуры
                }
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }


    private static Map<Integer, List<String>> createOrUpdateNomenclatureAfterReadExcelFile(String path) throws IOException {
        //Create the input stream from the xlsx/xls file
        FileInputStream fis = new FileInputStream(path);

        //Create Workbook instance for xlsx/xls file input stream
        Workbook workbook = null;

        if (path.toLowerCase().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(fis);
        } else if (path.toLowerCase().endsWith("xls")) {
            workbook = new HSSFWorkbook(fis);
        }

        Map<Integer, List<String>> data = new HashMap<>();
        int count = 0;
        //Get the number of sheets in the xlsx file
        int numberOfSheets;

        try {
            numberOfSheets = workbook.getNumberOfSheets();
            //loop through each of the sheets
            for (int i = 0; i < numberOfSheets; i++) {

                //Get the nth sheet from the workbook
                Sheet sheet = workbook.getSheetAt(i);

                for (Row row : sheet) {
                    data.put(count, new ArrayList<>());

                    for (Cell cell : row) {
                        switch (cell.getCellTypeEnum()) {
                            case NUMERIC:
                                data.get(count).add(String.valueOf(cell.getNumericCellValue()));
                                break;
                            case STRING:
                                data.get(count).add(String.valueOf(cell.getStringCellValue()));
                                break;
                            default:
                                data.get(count).add(" ");
                        }
                    }
                    count++;
                }
            }

            fis.close();
            return data;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return data;
    }
}
