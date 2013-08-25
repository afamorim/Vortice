package com.vortice.core.util.arquivo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.vortice.core.exception.AmbienteException;

public class ZipUtil {
	
	private static final Logger LOG = Logger.getLogger(ZipUtil.class);
    
	public static byte[] criarZip(byte[] bytes) throws AmbienteException{

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			File arquivo = ArquivoUtil.criarArquivoTemporario("arquivoOriginal", ".txt");
			FileOutputStream out = new FileOutputStream(arquivo);
			out.write(bytes);
			out.close();
			File arquivoZip = ArquivoUtil.criarArquivoTemporario("arquivoZipado", ".zip");
			fos = new FileOutputStream( arquivoZip );
			bos = new BufferedOutputStream(fos, 1024);
			criarZip( bos, arquivo );
			bos.close();
			return ArquivoUtil.getBytesFromFile(arquivoZip);
		}catch(Exception e){
			throw new AmbienteException(e);
		}finally {
			if( bos != null ) {
				try {
					bos.close();
				} catch( Exception e ) {}
			}
			if( fos != null ) {
				try {
					fos.close();
				} catch( Exception e ) {}
			}
		}
	}
	
	public static byte[] criarZip(String nome, byte[] bytes) throws AmbienteException{
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			File arquivo = new File(nome);
			FileOutputStream out = new FileOutputStream(arquivo);
			out.write(bytes);
			out.close();
			//adiciona a extensao .zip no arquivo, caso nao exista
			File arquivoZip = new File(nome + ".zip");
			fos = new FileOutputStream( arquivoZip );
			bos = new BufferedOutputStream(fos, 1024);
			criarZip( bos, arquivo );
			bos.close();
			arquivo.deleteOnExit();
			
			return ArquivoUtil.getBytesFromFile(arquivoZip);
		}catch(Exception e){
			throw new AmbienteException(e);
		}finally {
			if( bos != null ) {
				try {
					bos.close();
				} catch( Exception e ) {}
			}
			if( fos != null ) {
				try {
					fos.close();
				} catch( Exception e ) {}
			}
		}
	}
	
	public static byte[] criarZip(List<Arquivo> arquivos, String nomeArquivo) throws AmbienteException{
		File arquivoZip = new File(nomeArquivo);
		
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(arquivoZip));
			for (Arquivo arquivo : arquivos){
				//File farquivo = new File(arquivo.getNome());
				//FileInputStream in = new FileInputStream(farquivo);
				out.putNextEntry(new ZipEntry(arquivo.getNome()));
				out.write(arquivo.getBytes());
				
				out.closeEntry();
	            //in.close();
	            //farquivo.deleteOnExit();
			}
			out.close();
			return ArquivoUtil.getBytesFromFile(arquivoZip);
		} catch (Exception e) {
			LOG.error("Erro no momento de zipar os arquivo em um unico arquivo");
			throw new AmbienteException("Erro no momento de zipar os arquivo em um unico arquivo", e);
		}
	}
	   
	public static List criarZip( OutputStream os, File arquivo ) throws AmbienteException {
		List listaEntradasZip = new ArrayList();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream( os );
			String caminhoInicial = arquivo.getParent();
			LOG.debug("caminhoInicial > " + caminhoInicial);
			LOG.debug("getAbsolutePath > " + arquivo.getAbsolutePath());
			List novasEntradas = adicionarArquivoNoZip( zos, arquivo, caminhoInicial );
			if( novasEntradas != null ) {
				listaEntradasZip.addAll( novasEntradas );
			}
		}
		finally {
			if( zos != null ) {
				try {
					zos.close();
				} catch( Exception e ) {}
				}
		}
		return listaEntradasZip;
	}
	
	public static List<ZipEntry> adicionarArquivoNoZip( ZipOutputStream zos, File arquivo, String caminhoInicial ) throws AmbienteException {
		List<ZipEntry> listaEntradasZip = new ArrayList<ZipEntry>();
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		byte buffer[] = new byte[1024];
		try {
			String caminhoEntradaZip = null;
			ZipEntry entrada = null;
			if (caminhoInicial != null){
				int idx = arquivo.getAbsolutePath().indexOf(caminhoInicial);
				if( idx >= 0 ) {
					caminhoEntradaZip = arquivo.getAbsolutePath().substring( idx+caminhoInicial.length()+1 );
				}
				entrada = new ZipEntry( caminhoEntradaZip );
			}else{
				entrada = new ZipEntry(arquivo.getPath());
			}
			zos.putNextEntry( entrada );
			zos.setMethod( ZipOutputStream.DEFLATED );
			fis = new FileInputStream( arquivo );
			bis = new BufferedInputStream( fis, 1024 );
			int bytesLidos = 0;
			while((bytesLidos = bis.read(buffer, 0, 1024)) != -1) {
				zos.write( buffer, 0, bytesLidos );
			}
			listaEntradasZip.add( entrada );
		}catch(IOException e){
			throw new AmbienteException(e);
		}
		if( fis != null ) {
			try {
				fis.close();
			} catch( Exception e ) {}
		}
		return listaEntradasZip;
	}
	
  
		      
	public static ArrayList<File> descompactarArquivo(File arquivoZip, String nomeArquivo) throws AmbienteException{
		ZipFile zip = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] buffer = new byte[1024];
		ArrayList<File> arrayFile = new ArrayList<File>();
		try {
			zip = new ZipFile( arquivoZip );
			Enumeration e = zip.entries();
			while( e.hasMoreElements () ) {
				ZipEntry entrada = (ZipEntry) e.nextElement();
				LOG.debug("arquivoZip.getName() " + arquivoZip.getName());
				String sufix = arquivoZip.getName().substring(0, nomeArquivo.lastIndexOf("."));
				String prefix = arquivoZip.getName().substring(nomeArquivo.lastIndexOf(".")+1);
				LOG.debug("Sufix " + sufix);
				LOG.debug("Prefix " + prefix);
				File arquivo = File.createTempFile(sufix, prefix);
				try {
					//le o arquivo do zip e grava em disco
					is = zip.getInputStream( entrada );
					os = new FileOutputStream( arquivo );
					int bytesLidos = 0;
					if( is == null ) {
						throw new ZipException("Erro ao ler a entrada do zip: " + entrada.getName());
					}
					while( (bytesLidos = is.read( buffer )) > 0 ) {
						os.write( buffer, 0, bytesLidos );
					}
					arrayFile.add(arquivo);
				} finally {
					if( is != null ) {
						try {
							is.close();
						} catch( Exception ex ) {}
					}
					if( os != null ) {
						try {
							os.close();
						} catch( Exception ex ) {}
					}
				}
			}
			arquivoZip.delete();
			return arrayFile;
		}catch(ZipException e){
			throw new AmbienteException(e);
		}catch(IOException e){
			throw new AmbienteException(e);
		}finally {
			if( zip != null ) {
				try {
					zip.close();
				} catch( Exception e ) {}
			}
		}
	}
}