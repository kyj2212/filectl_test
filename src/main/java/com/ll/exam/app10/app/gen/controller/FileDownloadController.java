package com.ll.exam.app10.app.gen.controller;

import com.ll.exam.app10.app.article.entity.Article;
import com.ll.exam.app10.app.gen.entity.GenFile;
import com.ll.exam.app10.app.gen.service.GenFileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
@RequestMapping("/download")
@RequiredArgsConstructor
public class FileDownloadController {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    private final GenFileService genFileService;

    @GetMapping("/gen/{id}")
    @SneakyThrows
    public void download(HttpServletResponse response, @PathVariable Long id) {


        GenFile genFile = genFileService.getById(id).get();

        String path = genFile.getFilePath();

        File file = new File(path);
        response.setHeader("Content-Disposition", "attachment;filename=" + genFile.getOriginFileName());

        FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
        OutputStream out = response.getOutputStream();

        int read = 0;
        byte[] buffer = new byte[1024];
        while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을 파일이 없음
            out.write(buffer, 0, read);
        }

    }
}
