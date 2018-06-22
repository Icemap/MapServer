package com.cheese.MapServer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils
{
	public static Integer readTideInfo(String fileName,String datetime)
	{
		File file = new File(fileName);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null)
			{
				String[] tempArray = tempString.split(",");
				if(tempArray[2].trim().equals("tide" + datetime + ".nc"))
					return Integer.parseInt(tempArray[0]);
			}
			reader.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} 
				catch (IOException e1)
				{
				}
			}
		}
		
		return null;
	}
	
	public static String readFileByLines(String fileName)
	{
		StringBuilder sb = new StringBuilder();
		File file = new File(fileName);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null)
			{
				sb.append(tempString);
			}
			reader.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} 
				catch (IOException e1)
				{
				}
			}
		}
		
		return sb.toString();
	}
	
	public static String readResourcesByLines(String path)
	{
		Resource res = new ClassPathResource(path);
		
		try
		{
			return readFileByUtf8(res.getFile().getAbsolutePath());
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String readFileByUtf8(String path) throws IOException
	{
		StringBuffer sb = new StringBuffer();
		FileInputStream fis = new FileInputStream(path);   
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
		BufferedReader br = new BufferedReader(isr);   
		String line = null;   
		while ((line = br.readLine()) != null) 
		{   
			sb.append(line);
		} 
		br.close();
		return sb.toString();
	}
	
	public static String saveFile(MultipartFile file, String path)
	{
		String extName = file.getOriginalFilename().substring(file.
				getOriginalFilename().lastIndexOf(".")).toLowerCase(); 
		String fileName = new Date().getTime() + extName;
		
		saveFile(safeGetInputStream(file), fileName, path);
		return fileName;
	}
	
	public static String saveFile(InputStream inputStream, String fileName,String path) 
	{
		String savePath = "";

		OutputStream os = null;
		try
		{
			byte[] bs = new byte[1024];
			int len;

			File tempFile = new File(path);
			if (!tempFile.exists())
			{
				tempFile.mkdirs();
			}

			savePath = path + fileName;
			os = new FileOutputStream(savePath);
			while ((len = inputStream.read(bs)) != -1)
			{
				os.write(bs, 0, len);
			}

		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try
			{
				if(os != null)
					os.close();
				if(inputStream != null)
					inputStream.close();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return fileName;
    }
	
	public static String savePic(InputStream inputStream, String fileName,String path) 
	{
		return saveFile(inputStream, fileName, path);
    }
	
	public static String readResourcesByLinesWithEnter(String path)
	{
		Resource res = new ClassPathResource(path);
		
		try
		{
			return readFileByUtf8WithEnter(res.getFile().getAbsolutePath());
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String readFileByUtf8WithEnter(String path) throws IOException
	{
		StringBuffer sb = new StringBuffer();
		FileInputStream fis = new FileInputStream(path);   
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
		BufferedReader br = new BufferedReader(isr);   
		String line = null;   
		while ((line = br.readLine()) != null) 
		{   
			sb.append(line).append(System.getProperty("line.separator"));
		} 
		br.close();
		return sb.toString();
	}
	
	public static InputStream safeGetInputStream(MultipartFile file)
	{
		InputStream is = null;
		try
		{
			is = file.getInputStream();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return is;
	}
}
