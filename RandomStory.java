/**
 * @author ${hisham_maged10}
 *https://github.com/hisham-maged10
 * ${DesktopApps}
 */
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
public class RandomStory
{
	private ArrayList<String> used;
	private HashMap<String,ArrayList<String>> words;
	private String[] template;
	private Random randObj;
	public RandomStory()
	{
		initialize(getDirectory());
	}
	public RandomStory(String filesDirectory)
	{
		initialize(filesDirectory);
	}
	private ArrayList<String> filterWhiteSpace(ArrayList<String> arrayList)
	{
		do{
			if(arrayList.contains(" "))
			arrayList.remove(arrayList.indexOf(" "));
		}while(arrayList.contains(" "));
		return new ArrayList<String>(arrayList);
	}
	public int getNumberOfReplacedWords()
	{
		return this.used.size();
	}
	private String getDirectory()
	{
		JFileChooser chooser=new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		try{
		do
		{
			System.out.println("Please choose a directory that contains the files!");
			chooser.showOpenDialog(null);
		}while(chooser.getSelectedFile()==null && !chooser.getSelectedFile().isDirectory());
		}catch(NullPointerException ex){System.out.println("Incorrect Response!"); return getDirectory();}
		return chooser.getSelectedFile().getPath();
	}
	private void initialize(String filesDirectory)
	{
		randObj=new Random();
		template=getTemplate(filesDirectory+"/madtemplate"+(randObj.nextInt(4)+1)+".txt");
		String[] files={"noun","verb","adjective","color","country","timeframe","number","animal","name","fruit"};
		words=new HashMap<>();
		for(String file:files)
			words.put(file,fillArr(filesDirectory+"/"+file+".txt"));
		
		used=new ArrayList<String>();
	}
	private String[] getTemplate(String path)
	{
		File source=new File(path);
		if(!source.isFile()){System.out.println("Incorrect file"); return null;}
		return getLines(source);
	}
	private String[] getLines(File source)
	{
		ArrayList<String> lines=new ArrayList<String>();
		if(!source.isFile()){System.out.println("Incorrect file"); return null;}
		String line;
		try(BufferedReader input=new BufferedReader(new FileReader(source)))
		{
			while((line=input.readLine())!=null)
			{
				lines.add(line);
			}	
		}catch(IOException ex){ex.printStackTrace();}
		return lines.toArray(new String[lines.size()]);
	}
	private ArrayList<String> fillArr(String path)
	{
		File file=new File(path);
		if(!file.isFile()){System.out.println("Incorrect file"); return null;}
		String[] lines=getLines(file);
		return filterWhiteSpace(new ArrayList<String>(Arrays.asList(lines)));
	}
	public String makeStory()
	{
		String[] tokens=null;
		int j=0;
		StringBuilder sb=new StringBuilder();
		for(String e: template)
		{
			tokens=e.trim().split(" ");
			j=0;
			for(String e1:tokens)
			{
				if(processWord(e1))tokens[j]=replaceLabel(e1);
				sb.append(tokens[j++]+" ");
			}
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	private boolean processWord(String word)
	{
		int index;
		if((index=word.indexOf("<"))!=-1 && word.indexOf(">",index+1)!=-1)
			return true;
		else
			return false;
	}
	private String replaceLabel(String label)
	{
		String temp=label;
		label=label.substring(label.indexOf("<")+1,label.indexOf(">"));
		temp=temp.substring(temp.indexOf(">")+1);
		return randomFrom(words.get(label.toLowerCase()))+temp;
	}
	private String randomFrom(ArrayList<String> labelArr)
	{
		String needed="";
		do
		{
			needed=labelArr.get(randObj.nextInt(labelArr.size()));
		}while(used.contains(needed));
		used.add(needed);
		return needed;
	}
}