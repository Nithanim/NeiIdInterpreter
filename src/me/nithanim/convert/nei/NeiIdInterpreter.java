package me.nithanim.convert.nei;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NeiIdInterpreter
{
	
	List<Group> groups = new ArrayList<Group>();
	private File file;
	
	public static void main(String[] args)
	{
		System.out.println("This program displays outputted ids of NEI as id-groups. By: Nithanim");
		
		if(args.length >= 1)
		{
			try
			{
				new NeiIdInterpreter(new File(args[0]));
			}
			catch (FileNotFoundException e)
			{
				System.out.println("File was not found: \"" + args[0]);
			}
		}
		else
		{
			try
			{
				new NeiIdInterpreter(new File("ids.txt"));
			}
			catch (FileNotFoundException e)
			{
				System.out.println("Standard file was not found: \"ids.txt\"");
				System.out.println("You can specify a file as an argument.");
			}
		}
	}
	
	
	public NeiIdInterpreter(File file) throws FileNotFoundException
	{
		this.file = file;
		
		createGroups();
		
		//user input
		Scanner s = new Scanner(System.in);
		
		while(true)
		{
			System.out.println("\"free\", \"taken\", \"reload\" or \"quit\"");
			String line = s.nextLine();
			
			if(line.equals("free"))
			{
				displayInverseGroups();
			}
			else if(line.equals("taken"))
			{
				displayGroups();
			}
			else if(line.equals("reload"))
			{
				groups.clear();
				createGroups();
				System.out.println("done!");
			}
			else if(line.equals("quit"))
			{
				s.close();
				System.exit(0);
			}
			else
			{
				System.out.println("unknown command!");
			}
		}
	}

	/**
	 * Reloads groups from id-file
	 * @throws FileNotFoundException
	 */
	private void createGroups() throws FileNotFoundException
	{
		Scanner s = new Scanner(file);
		String line;
		
		int groupStart = 1;
		int lastid = 0;
		int currid;
		
		Group g;
		
		while(s.hasNextLine()) //loop through lines
		{
			line = s.nextLine();
			String[] split = line.split(" ");
			currid = Integer.parseInt(split[split.length-1]); //last string is id
			
			
			if(lastid+1 != currid) //new group began
			{
				g = new Group(groupStart, lastid);
				groups.add(g);
				
				groupStart = currid;
			}
			
			
			lastid = currid;
		}
		s.close();
	}


	/**
	 * Displays a list of taken ids
	 */
	private void displayGroups()
	{
		System.out.println("Taken ids:");
		for(Group g : groups)
		{
			System.out.printf("%5d - %5d = %4d %n", g.start, g.end, g.end-g.start);
		}
	}
	
	/**
	 * Displays a list of free ids
	 */
	private void displayInverseGroups()
	{
		System.out.println("Free ids:");
		for(int i=0+1 ; i<groups.size()-1 ; i++)
		{
			int start = groups.get(i-1).end + 1;
			int end = groups.get(i).start - 1;
			
			System.out.printf("%5d - %5d = %5d%n", start, end, end-start + 1);
		}
	}
}
