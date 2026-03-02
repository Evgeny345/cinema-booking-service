package ru.kuzin.CornCinema.utilites;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class ImageUploadService {
	
	@Value("${upload_movie_poster.path}")
	private String posterUploadPath;
	@Value("${upload_qr_code.path}")
	private String qrUploadPath;
	@Value("${cinema.poster.height:500}")
	private Integer posterHeight;
	@Value("${cinema.poster.width:350}")
	private Integer posterWidth;
	
	public String saveFileAndReturnUniqueTitle(MultipartFile file, String movieTitle) {
		String fileName = null;
		Path totalDirectory;
		if (file.getContentType().equals("image/png")||(file.getContentType().equals("image/jpeg"))) {
			try {
				Path tempPath = Paths.get(posterUploadPath + "/" + directoryNameWithoutSpace(movieTitle));
				totalDirectory = Files.exists(tempPath)? tempPath: Files.createDirectory(tempPath);
				String UUIDfile = UUID.randomUUID().toString();
				fileName = StringUtils.cleanPath(UUIDfile + "." + file.getOriginalFilename());
				Path location = totalDirectory.resolve(fileName);
				Files.copy(file.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
				
				
			} catch (IOException e) {
				e.printStackTrace();
				fileName = null;
			}
		}
		
		return fileName;
	}
	
	public String saveResizeFileAndReturnUniqueTitle(MultipartFile file, String movieTitle) {
		String fileFormat = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		String posterTitle;
		
		File uploadDirectory = new File(posterUploadPath + "/" + directoryNameWithoutSpace(movieTitle));
		if (!uploadDirectory.exists()) {
			uploadDirectory.mkdir();
		}
		try {		
			BufferedImage inputImage1 = ImageIO.read(file.getInputStream());
			BufferedImage outputImage = new BufferedImage(posterWidth, posterHeight, inputImage1.getType());
			Graphics2D g2d = outputImage.createGraphics();
	        g2d.drawImage(inputImage1, 0, 0, posterWidth, posterHeight, null);
	        g2d.dispose();
	        String UUIDfile = UUID.randomUUID().toString();
	        posterTitle = StringUtils.cleanPath(UUIDfile + "." + file.getOriginalFilename());
	        ImageIO.write(outputImage, fileFormat, new File(uploadDirectory, "/" + posterTitle));
		} catch (IOException e) {
			e.printStackTrace();
			posterTitle = null;
		}
		return posterTitle;
	}
	
	public void createDirectoryAndMoveFileToIt(String newDirectory, String oldDirectory, String fileName) {
		Path sourceDir = Paths.get(posterUploadPath + "/" + directoryNameWithoutSpace(oldDirectory));
		Path newDir = Paths.get(posterUploadPath + "/" + directoryNameWithoutSpace(newDirectory));	
		try {
			Files.createDirectories(newDir);
			Files.move(sourceDir.resolve(fileName), newDir.resolve(fileName) ,StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
			deletePosterDirectory(oldDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void deletePosterDirectory(String directoryName) {
		Path directory = Paths.get(posterUploadPath + "/" + directoryNameWithoutSpace(directoryName));
		System.out.println(directory.toString());
		deleteDirectory(directory);
	}
	
	public void deleteQrCode(LocalDateTime resirvationDate, Integer userId) {
		String formatedDate = resirvationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
		Path directory = Paths.get(qrUploadPath + "/" + userId.toString() + "/" + formatedDate);
		deleteDirectory(directory);
	}
	
	public void createQRcode(Integer userId, LocalDateTime registrationDate, List<Long> ticketsId) {

		String sampleOnLeght = "0000000000";
		List<String> ticIdAsStr = ticketsId.stream()
				.map(id -> sampleOnLeght.substring(0, sampleOnLeght.length()-id.toString().length()) + id.toString()).toList();
		String result = String.join(System.lineSeparator(), ticIdAsStr);
		String formatedDate = registrationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
		String uniqFileName = formatedDate + ".png";
        
		File uploadDirectory = new File(qrUploadPath + "/" + userId.toString() + "/" + formatedDate);
        
		if (!uploadDirectory.exists()) {
    	    uploadDirectory.mkdirs();
        }
		
        QRCodeWriter barcodeWriter = new QRCodeWriter();
	    BitMatrix bitMatrix = null;
	   
	    try {
		    bitMatrix = barcodeWriter.encode(result, BarcodeFormat.QR_CODE, 300, 300);
	    } catch (WriterException e) {
		    e.printStackTrace();
	    }

        try {
			ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix), "png", new File(uploadDirectory, "/" + uniqFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String directoryNameWithoutSpace(String filmTitle) {
		return filmTitle.replaceAll("\\s","");
	}
	
	private void deleteDirectory(Path directory) {
		if (Files.exists(directory))
	    {
	        try {
				Files.walkFileTree(directory, new SimpleFileVisitor<Path>()
				{
				    @Override
				    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException
				    {
				        Files.delete(path);
				        return FileVisitResult.CONTINUE;
				    }

				    @Override
				    public FileVisitResult postVisitDirectory(Path directory, IOException ioException) throws IOException
				    {
				        Files.delete(directory);
				        return FileVisitResult.CONTINUE;
				    }
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}

}
