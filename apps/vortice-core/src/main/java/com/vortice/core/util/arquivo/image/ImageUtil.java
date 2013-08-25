package com.vortice.core.util.arquivo.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//import com.vortice.core.exception.AmbienteException;
//import com.vortice.core.exception.AplicacaoException;
//import com.vortice.core.util.arquivo.ArquivoUtil;

public class ImageUtil {
	
	private static final Logger LOG = Logger.getLogger(ImageUtil.class);
	
//	public static byte[] redimensionarImagem(byte[] bytes, int altura, int largura, String nomeImagem, boolean manterProporcao) 
//		throws AmbienteException, AplicacaoException
//	{
//		int quality = 80; // Qualidade da imagem [0~100]
//		
//		Image imagem = new ImageIcon(bytes).getImage();
//		return redimensionar(imagem, largura, altura, quality, nomeImagem, manterProporcao);
//	}
	
//	private static byte[] redimensionar(Image image, int width, int height, int quality,
//		String nomeImagem, boolean manterProporcao) throws AmbienteException, AplicacaoException
//	{
//		// Calculos necessários para manter as propoçoes da imagem, conhecido
//		// como "aspect ratio"
//		if (manterProporcao)
//		{
//			double thumbRatio = (double) width / (double) height;
//			int imageWidth = image.getWidth(null);
//			int imageHeight = image.getHeight(null);
//	
//			double imageRatio = (double) imageWidth / (double) imageHeight;
//	
//			if (thumbRatio < imageRatio) {
//				height = (int) (width / imageRatio);
//			} else {
//				width = (int) (height * imageRatio);
//			}
//			// Fim do cálculo
//		}
//		BufferedImage thumbImage = new BufferedImage(width, height,
//				BufferedImage.TYPE_INT_RGB);
//		Graphics2D graphics2D = thumbImage.createGraphics();
//
//		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//		graphics2D.drawImage(image, 0, 0, width, height, null);
//
//		BufferedOutputStream out;
//		try {
//			File tempFile = File.createTempFile(nomeImagem.substring(0 , nomeImagem.lastIndexOf(".")), "jpg");
//			tempFile.deleteOnExit();
//			out = new BufferedOutputStream(new FileOutputStream(tempFile));
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			JPEGEncodeParam param = encoder
//					.getDefaultJPEGEncodeParam(thumbImage);
//			quality = Math.max(0, Math.min(quality, 100));
//			param.setQuality((float) quality / 100.0f, false);
//			encoder.setJPEGEncodeParam(param);
//			encoder.encode(thumbImage);
//			byte[] bytes = ArquivoUtil.getBytesFromFile(tempFile);
//			out.close();
//			return bytes;
//		} catch (FileNotFoundException e) {
//			LOG.error("FileNotFoundException " + e.getMessage());
//			throw new AmbienteException(e);
//		} catch (IOException e) {
//			LOG.error("IOException " + e.getMessage());
//			throw new AmbienteException(e);
//		}
//	}
}