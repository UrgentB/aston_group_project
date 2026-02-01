package org.example;

import org.example.Read_From_File.ReadFromTxtFile;
import org.example.Validator.BusValidator;
import org.example.application.BusExportService;
import org.example.application.BusImportService;
import org.example.application.BusServiceImpl;
import org.example.application.ExportService;
import org.example.application.InputType;
import org.example.application.Readable;
import org.example.application.Service;
import org.example.application.sorting.SortAlgorithm;
import org.example.application.sorting.SortCondition;
import org.example.application.sorting.SortType;
import org.example.domain.Bus;
import org.example.infrastructure.CustomList;
import org.example.presentation.ApplicationController;

public class App {

    public void run() {

        final ExportService<Bus> exportService = new BusExportService(true);

        final Readable readable = new ReadFromTxtFile();
        final BusValidator busValidator = new BusValidator();
        final BusImportService importService = BusImportService.getInstance();
        importService.setReadable(readable);
        importService.setValidator(busValidator);
        final ApplicationController applicationController = new ApplicationController();
        final Service<Bus> service = new BusServiceImpl(exportService);

        while(true) {
            String inputPath;
            InputType inputType = applicationController.askInputType();
            Boolean useStreams = applicationController.askBoolean("Использовать заполнение через стримы?");
            if (inputType == InputType.INPUT_FROM_FILE) {
                inputPath = applicationController.askString("Введите путь к файлу: ");
                importService.setPath(inputPath);
            }
            SortType sortType = applicationController.askSortType();
            SortAlgorithm sortAlgorithm = applicationController.askSortAlgorithm();
            SortCondition sortCondition = applicationController.askSortCondition();
            Boolean performExport = applicationController.askBoolean("Экспортировать в файл?");
            if (performExport) {
                exportService.setPath(applicationController.askString("Input output file path."));
            }
            
            CustomList<Bus> buses;
            if (useStreams) {
                buses = service.streamRead(inputType);
            } else {
                buses = service.read(inputType);
            }
            
            CustomList<Bus> sortedBuses = service.sort(buses, sortType, sortAlgorithm, sortCondition);
            // TODO: Add multithreading
            if (performExport) {
                exportService.save(sortedBuses);
            }

            applicationController.showList(sortedBuses);
            
        }        
    }

}
