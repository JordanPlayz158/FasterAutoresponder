package me.JordanPlayz158.Util;

//Utils class
public class Utils
{
	//Adds the strings together
	public static String addStrings(String... strings)
	{
		//The result
		String result = "";

		//Every element
		for (String s : strings)
		{
			//Add the element to the result
			result.concat(s);
		}

		//You know what this mean lol (god I can't make serious comments)
		return result;
	}
}