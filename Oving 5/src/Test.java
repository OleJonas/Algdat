import java.io.FileReader;
import java.io.BufferedReader;

class Test{
	public static void main(String[] args) throws Exception{ 
		FileReader in = new FileReader("./navn.txt");
		BufferedReader buf = new BufferedReader(in); 
		int lines = 0;

		while(buf.readLine() != null) lines++;
		System.out.println(lines);
	}
}