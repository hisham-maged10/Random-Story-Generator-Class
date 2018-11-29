/**
 * @author ${hisham_maged10}
 *https://github.com/hisham-maged10
 * ${DesktopApps}
 */
public class TestRandomStory
{
	public static void main(String[] args)
	{
		testing();
	}	
	public static void testing()
	{
		System.out.println();
		RandomStory rs = new RandomStory("myData");
		System.out.println("Randomly Generated Story\n"
				    +"------------------------");
		System.out.println(rs.makeStory());
		System.out.println("Numbers of replaced words: "+rs.getNumberOfReplacedWords());
	}
}