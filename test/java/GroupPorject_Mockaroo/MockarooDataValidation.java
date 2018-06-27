package GroupPorject_Mockaroo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mifmif.common.regex.util.Iterator;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarooDataValidation {
	WebDriver driver;
	String Title;
	boolean header;
	boolean BOM;
	List<String> citys;
	List<String> counrtries;
	Set<String> citiesSet;
	Set<String> countriesSet;

	// @BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get(" https://mockaroo.com/");
		driver.manage().window().maximize();
	}

	// @Test
	public void mockarooDateValidation() throws InterruptedException {
		Title = driver.getTitle();
		Assert.assertEquals(Title, "Mockaroo - Random Data Generator and API Mocking Tool | JSON / CSV / SQL / Excel");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='brand']")).getText(), "mockaroo");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='tagline']")).getText(),
				"realistic data generator");
		List<WebElement> list = driver.findElements(By.xpath("//a[@class='close remove-field remove_nested_fields']"));
		Remove_Field(list);
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[@class='column column-header column-name']")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[@class='column column-header column-type']")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[@class='column column-header column-options']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[.='Add another field']")).isEnabled());
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='num_rows']")).getAttribute("value"), "1000");
		Assert.assertTrue(driver.findElement(By.xpath("//option[@value='csv']")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//option[.='Unix (LF)']")).isSelected());
		header = driver.findElement(By.xpath("//input[@id='schema_include_header']")).isSelected();// true
		BOM = driver.findElement(By.xpath("//input[@id='schema_bom']")).isSelected();// false
		if (!header) {
			driver.findElement(By.xpath("//input[@id='schema_include_header']")).click();
			System.out.println(header);
			System.out.println(BOM);
		} else if (BOM) {
			driver.findElement(By.xpath("//input[@id='schema_bom']")).click();

		}
		assertTrue(header && !BOM);
		addFile();
		RepeatSteps12_14();
		Thread.sleep(500);
		driver.findElement(By.xpath("//button[@id='download'and @type='button']")).click();

	}

	@Test
	public void readFile() throws IOException {
		String filePath = "/Users/yuan/Downloads/MOCK_DATA (1).csv";
		int count = -1;
		try {
			FileReader rd = new FileReader(filePath);
			BufferedReader bf = new BufferedReader(rd);
			citys = new ArrayList<>();
			counrtries = new ArrayList<>();
			String line = "";
			String[] arr;

			while ((line = bf.readLine()) != null) {
				if (line.contains(",")) {
					arr = line.split(",");
					citys.add(arr[0]);
					counrtries.add(arr[1]);
				}
				count++;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(count == 1000);
		assertTrue(citys.get(0).equalsIgnoreCase("city"));
		assertTrue(counrtries.get(0).equalsIgnoreCase("Country"));
		longestNmae_shortestName(citys);
		How_many_times_each_Country_is_mentioned(counrtries);
		addAllCityesToHashSet();
		countUniquecities(citys, citiesSet);
		countUniquecities(counrtries, countriesSet);
		
	}

	// Sort all cities and find the city with the longest name and shortest name
	public void longestNmae_shortestName(List<String> citys) {
		Collections.sort(citys);
		int max = citys.get(0).length();

		for (String CityNameLength : citys) {
			if (CityNameLength.length() > max)
				max = CityNameLength.length();
		}

		int min = citys.get(0).length();
		for (String CityNameLength : citys) {
			if (CityNameLength.length() < min)
				min = CityNameLength.length();
		}

		System.out.println("City-Name: Maximum Length is " + max);
		System.out.println("City_Name: Minimum Length is " + min);

	}

	// In Countries ArrayList, find how many times each Country is mentioned. and
	// print out
	// ex: Indonesia-10
	// Russia-7 etc
	public void How_many_times_each_Country_is_mentioned(List<String> countries) {
		System.out.println("========================<How many times each country is mentioned>========================");
		List<String> eachCont = new ArrayList<>();
		Collections.sort(eachCont);
		for (String temp : countries) {
			eachCont.add(temp + ": " + Collections.frequency(countries, temp));
		}
		
		Set<String> set = new HashSet<>(eachCont);
		List<String> lis= new ArrayList<>(set);
		Collections.sort(lis);
		for(String each:lis) {
			System.out.println(each);
		}
			
	}
	//24. From file add all Cities to citiesSet HashSet
	//Set<String> citiesSet;
//	Set<String> countriesSet;
	public void addAllCityesToHashSet() throws IOException {
		System.out.println("========================<From file add all Cities to citiesSet HashSet>========================");
		FileReader filer= new FileReader("/Users/yuan/Downloads/MOCK_DATA (1).csv");
		BufferedReader bf= new BufferedReader(filer);
		citiesSet= new HashSet<>();
		countriesSet= new HashSet<>();
		String line="";
		while((line=bf.readLine())!=null) {
			String withComma=line;
			String[] withOutComma=withComma.split(",");
			citiesSet.add(withOutComma[0]);
			countriesSet.add(withOutComma[1]);
		}
		List <String> citi= new ArrayList<>(citiesSet);
		List <String> countr= new ArrayList<>(countriesSet);
		Collections.sort(citi);
		Collections.sort(countr);
		System.out.println("--------------------city--------------------");
		for(String eachCiti:citi) {
			System.out.println(eachCiti);
		}
		System.out.println("---------------------country--------------------");
		for(String eachCoun:countr) {
		System.out.println(eachCoun);
		}
		
		
	
	}
	//25. Count how many unique cities are in Cities ArrayList and assert that
	//it is matching with the count of citiesSet HashSet.
	
	public void countUniquecities(List<String> city,Set<String> citiSet ) {
		Set<String> countcity=new HashSet<>(city);
		List<String> countcitiSet= new ArrayList<>(countcity);
		assertEquals(countcitiSet.size(), citiSet.size());
	}

	public void addFile() {
		driver.findElement(By.xpath("//a[.='Add another field']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("(//input[@placeholder='enter name...'])[7]")).sendKeys("City");

		assertEquals(
				driver.findElement(By.xpath("(//input[@placeholder='choose type...'])[7]")).getAttribute("placeholder"),
				"choose type...");
		driver.findElement(By.xpath("(//input[@placeholder='choose type...'])[7]")).click();
		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("city");
		driver.findElement(By.xpath("//div[@class='type-name']")).click();
	}

	public void RepeatSteps12_14() throws InterruptedException {
		Thread.sleep(500);
		driver.findElement(By.xpath("(//a[@data-target='#fields'])[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("(//input[@placeholder='enter name...'])[8]")).clear();
		Thread.sleep(500);
		driver.findElement(By.xpath("(//input[@placeholder='enter name...'])[8]")).sendKeys("Country");
		Thread.sleep(500);

		driver.findElement(By.xpath("(//input[@placeholder='choose type...'])[8]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//input[@id='type_search_field']")).clear();
		Thread.sleep(500);
		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("Country");
		Thread.sleep(500);
		driver.findElement(By.xpath("//div[@class='type-name']")).click();
	}
	public void Remove_Field(List<WebElement> element) {
		List<WebElement> webEle = new ArrayList<>();
		webEle = element;

		for (WebElement etch : webEle) {
			etch.click();
		}

	}

}
