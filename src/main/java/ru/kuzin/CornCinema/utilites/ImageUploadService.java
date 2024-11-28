package ru.kuzin.CornCinema.utilites;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploadService {
	
	@Value("${upload.path}")
	private String uploadPath;
	
	public List<String> saveAllPicture(MultipartFile[] uploadingFiles, String filmTitle){
		List<String> listImgTitle = new ArrayList<>();
		Path totalDirectory;
		try {
			totalDirectory = Files.createDirectory(Paths.get(uploadPath + "/" + directoryName(filmTitle)));
			for (MultipartFile file: uploadingFiles) {
				String UUIDfile = UUID.randomUUID().toString();
				String fileName = StringUtils.cleanPath(UUIDfile + "." + file.getOriginalFilename());
				Path location = totalDirectory.resolve(fileName);
				Files.copy(file.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
				listImgTitle.add(fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listImgTitle;
	}
	
	public String saveFileAndReturnUniqueTitle(MultipartFile file, String filmTitle) {
		String fileName = null;
		Path totalDirectory;
		if (file.getContentType().equals("image/png")||(file.getContentType().equals("image/jpeg"))) {
			try {
				Path tempPath = Paths.get(uploadPath + "/" + directoryName(filmTitle));
				totalDirectory = Files.exists(tempPath)? tempPath: Files.createDirectory(tempPath);
				String UUIDfile = UUID.randomUUID().toString();
				fileName = StringUtils.cleanPath(UUIDfile + "." + file.getOriginalFilename());
				Path location = totalDirectory.resolve(fileName);
				System.out.println("Path location: " + location);
				Files.copy(file.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
				fileName = null;
			}
		}
		
		System.out.println("File name: " + fileName);
		return fileName;
	}
	
	private String directoryName(String filmTitle) {
		return filmTitle.replaceAll("\\s","");
	}

}
