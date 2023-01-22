package com.example.photocollagebackend.service;

import com.example.photocollagebackend.dto.CollageDTO;
import com.example.photocollagebackend.dto.Direction;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class CollageService {

    public InputStreamResource makeCollage(CollageDTO dto) {
        return createCollage(dto);
    }

    private InputStreamResource createCollage(CollageDTO dto) {
        BufferedImage[] images = new BufferedImage[dto.getFiles().size()];

        try {
            BufferedImage result;
            Color color = Color.decode(dto.getColor());
            BufferedImage borderStartEnd;
            BufferedImage borderPerp;

            if (dto.getDirection().equals(Direction.H)) {

                int minHeight = Integer.MAX_VALUE;
                int totalWidth = 0;

                for (int i = 0; i < images.length; i++) {
                    images[i] = ImageIO.read(new File(FileService.UPLOADED_FOLDER + "\\" + dto.getFiles().get(i)));
                    minHeight = Integer.min(minHeight, images[i].getHeight());
                }
                for (BufferedImage image : images) {
                    double ratio = (double) image.getHeight() / minHeight;
                    Graphics2D graphics2D = image.createGraphics();
                    graphics2D.drawImage(image, (int) (image.getWidth() * ratio), minHeight, null);
                    graphics2D.dispose();
                    totalWidth += image.getWidth();
                }
                borderStartEnd = new BufferedImage(totalWidth, dto.getBorder(), BufferedImage.TYPE_INT_RGB);
//                borderStartEnd.setRGB(color.getRed(), color.getGreen(), color.getBlue());
                borderPerp = new BufferedImage(dto.getBorder(), minHeight, BufferedImage.TYPE_INT_RGB);
//                borderPerp.setRGB(color.getRed(), color.getGreen(), color.getBlue());
                result = new BufferedImage(totalWidth + 2 * dto.getBorder(),
                        minHeight + 2 * dto.getBorder(), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D = result.createGraphics();
                graphics2D.drawImage(borderStartEnd, 0, 0, null);
                graphics2D.drawImage(borderStartEnd, 0, minHeight + dto.getBorder(), null);
                int temp = dto.getBorder();
                for (BufferedImage image : images) {
                    graphics2D.drawImage(image, temp, minHeight, null);
                    graphics2D.drawImage(borderPerp, temp + dto.getBorder(), minHeight, null);
                    temp += image.getWidth() + dto.getBorder();
                }
                graphics2D.dispose();
            }
            else {
                int minWidth = Integer.MAX_VALUE;
                int totalHeight = 0;

                for (int i = 0; i < images.length; i++) {
                    images[i] = ImageIO.read(new File(FileService.UPLOADED_FOLDER + "\\" + dto.getFiles().get(i)));
                    minWidth = Integer.min(minWidth, images[i].getWidth());
                }
                for (BufferedImage image : images) {
                    double ratio = (double) image.getHeight() / minWidth;
                    Graphics2D graphics2D = image.createGraphics();
                    graphics2D.drawImage(image, minWidth, (int) (image.getHeight() * ratio), null);
                    graphics2D.dispose();
                    totalHeight += image.getHeight();
                }
                borderStartEnd = new BufferedImage(dto.getBorder(), totalHeight, BufferedImage.TYPE_INT_RGB);
//                borderStartEnd.setRGB(color.getRed(), color.getGreen(), color.getBlue());
                borderPerp = new BufferedImage(minWidth, dto.getBorder(), BufferedImage.TYPE_INT_RGB);
//                borderPerp.setRGB(color.getRed(), color.getGreen(), color.getBlue());
                result = new BufferedImage(minWidth + 2 * dto.getBorder(),
                        totalHeight + 2 * dto.getBorder(), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D = result.createGraphics();
                graphics2D.drawImage(borderStartEnd, 0, 0, null);
                graphics2D.drawImage(borderStartEnd, minWidth + dto.getBorder(), 0, null);
                int temp = dto.getBorder();
                for (BufferedImage image : images) {
                    graphics2D.drawImage(image, minWidth, temp, null);
                    graphics2D.drawImage(borderPerp, minWidth, temp + dto.getBorder(), null);
                    temp += image.getHeight() + dto.getBorder();
                }
                graphics2D.dispose();
            }
            ImageIO.write(result, "jpg", new File("./out.jpg"));
            return new InputStreamResource(new FileInputStream("./out.jpg"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
