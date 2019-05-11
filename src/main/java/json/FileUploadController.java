package json;

import java.io.IOException;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import json.storage.StorageFileNotFoundException;
import json.storage.StorageService;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/uploads")
    public String listUploadedFiles(Model model) throws IOException {
        System.out.println("test1");
        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        System.out.println("test2");
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/uploads")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {
        System.out.println(file);

        String[] headers = {"Last Name", "First Name", "Maiden Name", "Date of Consecration", "Address Indicator",
                "Address 1", "Address 2", "City", "State", "Zip", "Country", "Primary Phone", "Email"};
        JSONConverter converter = new JSONConverter();
        storageService.store(file);
        System.out.println("test4");
        System.out.println(file.getOriginalFilename());
        String pathName = ("upload-dir\\" + file.getOriginalFilename());
        System.out.println("test 4.5");
        JSONArray arrayOfPeople = converter.JSONFromExcel(headers, pathName);
        System.out.println("test5");
        BufferedWriter writer = new BufferedWriter(new FileWriter("roster.json"));
        writer.write(arrayOfPeople.toJSONString());
        writer.close();

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/uploads";
//        return "Success";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}

