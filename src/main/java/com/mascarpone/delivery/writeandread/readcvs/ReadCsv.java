package com.mascarpone.delivery.writeandread.readcvs;

import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import com.mascarpone.delivery.entity.nomenclatureunit.NomenclatureUnit;
import com.mascarpone.delivery.service.nomenclature.NomenclatureService;
import com.mascarpone.delivery.service.nomenclatureunit.NomenclatureUnitService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReadCsv {//separator
    private final NomenclatureService nomenclatureService;
    private final NomenclatureUnitService nomenclatureUnitService;

    /**
     * @param path                 - путь к файлу .csv
     * @param numberColumnName     - номер столбца из файла с именем номенклатурной позиции
     * @param numberColumnQuantity - номер столбца из файла с количеством
     * @param numberColumnUnit     - номер столбца из файла с единицей измерения
     * @param countHeaderString-   число строк в шапке таблицы
     */
    public void createOrUpdateNomenclatureAfterReadCsvFile(String path, int numberColumnName, int numberColumnQuantity, int numberColumnUnit, int countHeaderString, char separator) {
        try {
            List<String[]> listOfCsv = null;

            listOfCsv = readCvsNomenclature(path, countHeaderString, separator); //List  массивов String, где каждый массив - прочитанная и разделенная по сепаратору строка csv-файла

            for (String[] i : listOfCsv) {
                Optional<Nomenclature> optional = nomenclatureService.getByName(i[numberColumnName - 1]);

                if (optional.isPresent()) { // если позиция номенклатуры с таким именем существует
                    Nomenclature nomenclature = optional.get();
                    nomenclature.setQuantity(Double.parseDouble(i[numberColumnQuantity - 1]));//установим заданное количество
                    //  nomenclatureService.save(nomenclature); //изменим позицию
                } else { // если позиция номенклатуры с таким именем отсутствует
                    Nomenclature nomenclature = new Nomenclature();
                    nomenclature.setName(i[numberColumnName - 1]); //установим имя
                    nomenclature.setQuantity(Double.parseDouble(i[numberColumnQuantity - 1]));//установим количество

                    Optional<NomenclatureUnit> optionalUnitNomenclature = nomenclatureUnitService.getByName(i[numberColumnUnit - 1]);

                    if (optionalUnitNomenclature.isPresent()) { //если такая единица измерения существует
                        nomenclature.setNomenclatureUnit(optionalUnitNomenclature.get()); //установим id ед.изм.
                    } else {//если такая единица измерения отсутствует
                        NomenclatureUnit unitNomenclature = new NomenclatureUnit();
                        unitNomenclature.setName(i[numberColumnUnit - 1]);
                        nomenclatureUnitService.save(unitNomenclature); //создаем ед.изм. и сохраняем ее
                        nomenclature.setNomenclatureUnit(unitNomenclature);//задаем номенклатурной позиции id ед.изм.
                    }

                    nomenclatureService.save(nomenclature); //сохраним созданный объект позиции номенклатуры
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path              - путь к файлу .csv
     * @param countHeaderString - число строк в шапке таблицы
     * @return - List  массивов String, где каждый массив - прочитанная и разделенная по сепаратору строка csv-файла
     * @throws Exception
     */
    private static List<String[]> readCvsNomenclature(String path, int countHeaderString, char separator) throws Exception {
        Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource(path).toURI()));

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator)
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(countHeaderString) //пропустит заданное количество строк при чтении
                .withCSVParser(parser)
                .build();

        List<String[]> listOfCsv = new ArrayList<>();
        String[] line;

        while ((line = csvReader.readNext()) != null) {//чтение csv-файла
            listOfCsv.add(line);
        }

        reader.close();
        csvReader.close();
        return listOfCsv;
    }
}
