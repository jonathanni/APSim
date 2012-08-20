package com.prodp.apsim.loader;

import java.io.*;
import java.net.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.prodp.apsim.APFinalData;

/**
 * The Class APDownloader.
 */
public class APDownloaderI586 extends JFrame{
	
	/**
	 * The type of download.
	 */
	public final static String TYPE = "i586"; //Either "64" or "i586"
	
	/** The jarloader. */
	public JLabel Indicator = new JLabel("Download Progress");
	
	/**
	 * The loading bar.
	 */
	
	public JProgressBar Loading = new JProgressBar(0, 100);
	
	/** The loader panel. */
	public JPanel full = new JPanel();
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7210604925986605167L;
	
	/* Clear all dll files */
	
	File dll1 = new File("j3dcore-ogl.dll");
	File dll2 = new File("j3dcore-ogl-cg.dll");
	File dll3 = new File("j3dcore-ogl-chk.dll");
	File dll4 = new File("j3dcore-d3d.dll");
	
	/**
	 * Inits the downloader.
	 */
	public void init(){
		
		if(dll1.exists()){
			dll1.delete();
			dll2.delete();
			dll3.delete();
			dll4.delete();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			setIconImage(ImageIO.read(APFinalData.class.getResource("../icons/apsim.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		URL TEST = null;
		try {
			TEST = new URL("http://apsim.dyndns.org/");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		URLConnection TEST_CONNECTION = null;
		try {
			TEST_CONNECTION = TEST.openConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		@SuppressWarnings("unused")
		InputStream TEST_INPUT = null;
		try {
			TEST_INPUT = TEST_CONNECTION.getInputStream();
		} catch (IOException e1) {
			setVisible(false);
			setTitle("INTERNET CONNECTION FAILED --- CANNOT DOWNLOAD APSIM");
			setVisible(true);
			return;
		}
		
			URL jarfile = null, batfile = null;
			try {
				jarfile = new URL("http://apsim.dyndns.org/productiveproductions/apsim/RunAP.jar");
				batfile = new URL("http://apsim.dyndns.org/productiveproductions/apsim/run.bat");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			URLConnection jartrans = null;
			URLConnection battrans = null;
			try {
				battrans = batfile.openConnection();
				jartrans = jarfile.openConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			full.add(Indicator);
			full.add(Loading);
			
			add(full);
			pack();
		
			setVisible(true);
			
			InputStream jar = null;
			InputStream bat = null;
			try {
				jar = jartrans.getInputStream();
				bat = battrans.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			OutputStream localjar = null;
			OutputStream localbat = null;
			try {
				localjar = new FileOutputStream("./RunAP.jar");
				localbat = new FileOutputStream("../run.bat");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			setVisible(false);
			setTitle("APDownloader - Downloading...");
			setVisible(true);
			
			try {
				readExtFileByByte(jar, (FileOutputStream) localjar, Loading, "JAR Bytes left: ");
				readExtFileByByte(bat, (FileOutputStream) localbat, Loading, "BAT Bytes left: ");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			URL libURL = null, dllURL = null;
			try {
				libURL = new URL("http://apsim.dyndns.org/productiveproductions/apsim/lib.zip");
				dllURL = new URL("http://apsim.dyndns.org/productiveproductions/apsim/dll"+TYPE+".zip");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			URLConnection libURLC = null;
			URLConnection dllURLC = null;
			try {
				libURLC = libURL.openConnection();
				dllURLC = dllURL.openConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			setVisible(false);
			setTitle("APDownloader - Wait...");
			setVisible(true);
			try {
				readExtZipFileByByte(new ZipInputStream(new BufferedInputStream(dllURLC.getInputStream())), "./");
				readExtZipFileByByte(new ZipInputStream(new BufferedInputStream(libURLC.getInputStream())), "./RunAP_lib/");
			} catch (IOException e) {
				e.printStackTrace();
			}
			setVisible(false);
			setTitle("APDownloader - DONE!");
			setVisible(true);
			
	}
	
	/**
	 * Read ext file by byte.
	 * 
	 * @param is
	 *            the is
	 * @param fos
	 *            the fos
	 * @param loader
	 *            the loader
	 * @param fullbytes
	 *            the fullbytes
	 * @param message
	 *            the message
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void readExtFileByByte(InputStream is, FileOutputStream fos, JProgressBar loader, String message) throws IOException {
		 int oneChar;
		 int counter = 1;
		 byte[] buf = new byte[1024];
		 int fullbytes = 0;

         while ((oneChar=is.read(buf)) > 0)
         {
        	if(counter == 1){
        		fullbytes = is.available();
        	}
        	fos.write(buf, 0, oneChar);
        	if(fullbytes != 0 && is.available() !=0 && counter !=0){
            	loader.setValue((int)((double)(fullbytes-is.available())/fullbytes*100));
            	counter++;
        	}
         }
         loader.setValue(100);
         is.close();
         fos.close();
		
	}
	
	private void readExtZipFileByByte(ZipInputStream is, String path) throws IOException {
		 File pathf = new File(path);
		 int oneChar;
		 BufferedOutputStream fos = null;
		 ZipEntry entry;
		 
		 pathf.mkdir();
		 
		 while((entry = is.getNextEntry()) != null){
			 
			 byte[] buf = new byte[1024];
			 fos = new BufferedOutputStream(new FileOutputStream(path+entry.getName()), 1024);
			 
        while ((oneChar=is.read(buf, 0, 1024)) > 0)
        {
           fos.write(buf, 0, oneChar);
        }
        fos.flush();
		 }
		 
		 
        is.close();
        fos.close();
		
	}

}