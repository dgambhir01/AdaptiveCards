package listeners;

import io.restassured.RestAssured;

import org.testng.IExecutionListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;

import static listeners.CardBodyStructure.*;

public class ResultListener implements ITestListener, ISuiteListener, IExecutionListener {

	private static long testCount = 0 ; 
	private List<ITestNGMethod> testMethods = null;
	static List<ITestNGMethod> passedtests = new ArrayList<ITestNGMethod>();
	static List<ITestNGMethod> failedtests = new ArrayList<ITestNGMethod>();
	static List<ITestNGMethod> skippedtests = new ArrayList<ITestNGMethod>();
	List<ItemsItem> TestCaseID = new ArrayList<>();
	List<ItemsItem> TestCaseName = new ArrayList<>();
	List<ItemsItem> TestCaseDesc = new ArrayList<>();
	List<ItemsItem> TestResult = new ArrayList<>();
	ITestContext context;
	static long startTime;
	static long endTime;
	
	public void setTestCount(long testCount){
	      ResultListener.testCount = testCount;
	 }

	 public long getTestCount(){
	     return ResultListener.testCount;
	 }

	 @Override
	 public void onStart(ISuite suite){
	       testMethods  = suite.getAllMethods();
	       ResultListener.testCount = testMethods.size();
	 }
	
	@Override
	public void onExecutionStart() {
		startTime = System.currentTimeMillis();
			}


	@Override
	public void onTestStart(ITestResult result) {

		TestCaseName.add(ItemsItem.builder().type("TextBlock").text(result.getMethod().getMethodName()).weight("Bolder")
				.wrap(true).isVisible(true).build());
		
		TestCaseDesc.add(ItemsItem.builder().type("TextBlock").text(result.getMethod().getDescription()).weight("Bolder")
				.wrap(true).isVisible(true).build());

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		context = result.getTestContext();
		
		passedtests.add(result.getMethod());

		TestCaseID.add(ItemsItem.builder().type("TextBlock").text(context.getAttribute("testId").toString())
				.weight("Bolder").isVisible(true).wrap(true).build());

		TestResult.add(ItemsItem.builder().type("TextBlock").text("PASS").weight("Bolder").isVisible(true).color("good")
				.wrap(true).build());
	}

	@Override
	public void onTestFailure(ITestResult result) {

		context = result.getTestContext();
		
		failedtests.add(result.getMethod());
		
		TestCaseID.add(ItemsItem.builder().type("TextBlock").text(context.getAttribute("testId").toString())
				.weight("Bolder").isVisible(true).wrap(true).build());

		TestResult.add(ItemsItem.builder().type("TextBlock").text("FAIL").weight("Bolder").isVisible(true)
				.color("attention").wrap(true).build());
	}
	
	@Override
    public void onTestSkipped(ITestResult result) {
		context = result.getTestContext();
		
       skippedtests.add(result.getMethod());
       
       TestCaseID.add(ItemsItem.builder().type("TextBlock").text(context.getAttribute("testId").toString())
				.weight("Bolder").isVisible(true).wrap(true).build());

		TestResult.add(ItemsItem.builder().type("TextBlock").text("SKIPPED").weight("Bolder").isVisible(true)
				.color("accent").wrap(true).build());
   } 

	@Override
	public void onFinish(ISuite suite) {
		endTime = System.currentTimeMillis();
		ResultBuilder builder = new ResultBuilder(TestCaseID, TestCaseName, TestCaseDesc, TestResult);

		// addYourWebHookUrl
		RestAssured.baseURI = "https://addYourWebHookUrl";
		RestAssured.given().log().body().body(builder.formBody()).when().post().then().log().body().statusCode(200);
	}
	
	public static String executionTime()
	{
		return String.valueOf(endTime- startTime);
	}
		
};