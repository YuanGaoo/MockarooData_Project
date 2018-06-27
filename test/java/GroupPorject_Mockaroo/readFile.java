package GroupPorject_Mockaroo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class readFile {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filePath="/Users/yuan/Downloads/MOCK_DATA (1).csv";
		try {
			FileReader rd= new FileReader(filePath);
			BufferedReader bf= new  BufferedReader(rd);
			System.out.println(bf.readLine());
			System.out.println(bf.readLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
