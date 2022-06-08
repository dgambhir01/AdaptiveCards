package Adaptive.Adaptive;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class Ok {
	@Test(description ="This is for ok")
	public static void AOne(ITestContext context){
	context.setAttribute("testId", 1);
	System.out.println("AOne");
	}



	@Test(description ="This is for ok2")
	public static void BTwo(ITestContext context){
		context.setAttribute("testId", 2);
		System.out.println("BTwo");
	}



	@Test(description ="This is for ok3")
	public static void CThree(ITestContext context){
		context.setAttribute("testId", 3);
		System.out.println("CThree");
	}
	
	@Test(description ="This is for ok4")
	public static void DFour(ITestContext context){
		context.setAttribute("testId", 4);
		System.out.println("DFour");
	}
	
	@Test(description ="This is for ok5")
	public static void registration(ITestContext context){
		context.setAttribute("testId", 5);
		System.out.println("Reg");
		Assert.assertFalse(true);
	}
	
	@Test(description ="This is for ok6")
	public static void trySkip(ITestContext context){
		context.setAttribute("testId", 7);
	    throw new SkipException("Skipping this exception");
	}




	
}
