package pl.jakubdudek.foodorderingappbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubdudek.foodorderingappbackend.util.FileManager;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileManager fileManager;

    @GetMapping("/{name}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String name,
            @RequestParam(defaultValue = "false", name = "cache") Boolean cache
    ) throws MalformedURLException {
        Resource file = fileManager.downloadFile(name);
        CacheControl cacheControl = cache ? CacheControl.maxAge(30, TimeUnit.DAYS).cachePublic() : CacheControl.noCache();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .cacheControl(cacheControl)
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }
}
