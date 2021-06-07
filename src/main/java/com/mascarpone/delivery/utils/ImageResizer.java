package com.mascarpone.delivery.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.mascarpone.delivery.utils.Constants.MAX_IMAGE_SIDE_SIZE;

public class ImageResizer {
    public static byte[] resizeImage(MultipartFile image) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image.getBytes()));
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();

        if (width > MAX_IMAGE_SIDE_SIZE && width > height) { // horizontal image
            double diff = (double) width / MAX_IMAGE_SIDE_SIZE;
            int newHeight = (int) Math.round(height / diff);

            bufferedImage = resize(bufferedImage, MAX_IMAGE_SIDE_SIZE, newHeight);
        } else if (height > MAX_IMAGE_SIDE_SIZE && height > width) { // vertical image
            double diff = (double) height / MAX_IMAGE_SIDE_SIZE;
            int newWidth = (int) Math.round(width / diff);

            bufferedImage = resize(bufferedImage, newWidth, MAX_IMAGE_SIDE_SIZE);
        } else if (height == width){
            bufferedImage = resize(bufferedImage, MAX_IMAGE_SIDE_SIZE, MAX_IMAGE_SIDE_SIZE);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);

        return baos.toByteArray();
    }

    private static BufferedImage resize(Image image, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();

        return bufferedImage;
    }
}
