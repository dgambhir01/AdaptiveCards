package listeners;
import java.util.Arrays;
import java.util.List;
import static listeners.CardBodyStructure.*;
public class ResultBuilder {

    List<ItemsItem> TestCaseName;
    List<ItemsItem> TestCaseDesc;
    List<ItemsItem> TestCaseID;
    List<ItemsItem> TestResult;

    public ResultBuilder(List<ItemsItem> testCaseID, List<ItemsItem> testCaseName, List<ItemsItem> testCaseDesc, List<ItemsItem> testResult) {
    	TestCaseID=testCaseID;
    	TestCaseName = testCaseName;
        TestCaseDesc=testCaseDesc;
        TestResult=testResult;

    }

    public CardBodyStructure formBody(){
        return builder()
                .type("message")
                .attachments(Arrays.asList(attachments()))
                .build();
    }

    public AttachmentsItem attachments(){

        return  AttachmentsItem.builder()
                .content(content())
                .contentType("application/vnd.microsoft.card.adaptive").build();

    }

    public Content content(){
        return Content.builder()
                .type("AdaptiveCard")
                .body(Arrays.asList(formHeaders(),infoLineItems(),testInfoHeaders(),testCaseData(),additionalStuff()))
                .build();
    }

    public BodyItem formHeaders(){

        ItemsItem itemsItem1 = ItemsItem.builder()
                .type("TextBlock")
                .size("Large")
                .isVisible(true)
                .weight("Bolder")
                .text("**UI Automation Test Summary**")
                .wrap(true)
                .style("heading")
                .build();

        ItemsItem itemsItem2 = ItemsItem.builder()
                .type("Image")
                .isVisible(true)
                .url("https://upload.wikimedia.org/wikipedia/commons/6/6a/L80385-flash-superhero-logo-1544.png")
                //.url("https://upload.wikimedia.org/wikipedia/commons/d/d6/Tavant_Logo_Color_No_Border.png")
                .height("40px")
                .build();

        ColumnsItem columnsItem1 = ColumnsItem.builder()
                .type("Column")
                .width("stretch")
                .items(Arrays.asList(itemsItem1)).build();

        ColumnsItem columnsItem2 = ColumnsItem.builder()
                .type("Column")
                .width("stretch")
                .items(Arrays.asList(itemsItem2)).build();



        ItemsItem itemsItem = ItemsItem.builder()
                .type("ColumnSet")
                .isVisible(true)
                .columns(Arrays.asList(columnsItem1,columnsItem2)).build();

       return  BodyItem.builder()
                .type("Container")
                .style("Tavant")
                .text("Test")
                .isVisible(true)
                .items(Arrays.asList(itemsItem))
                .build();


    }

    public BodyItem infoLineItems(){
    	
        ResultListener listener = new ResultListener();

        LineItem item1 = LineItem
                .builder()
                .title("Project Name")                
                .value( "**Finding - The Flash**").build();

        LineItem item2 = LineItem
                .builder()
                .title("Platform")
                .value("**Supernova**").build();

        LineItem item3 = LineItem
                .builder()
                .title("Execution Date Time")
                .value("**" + new java.util.Date() + "**").build();
        
        LineItem item4 = LineItem
                .builder()
                .title("TCs Count")
                .value("****" + String.valueOf(listener.getTestCount()) + "****").build();
        
        LineItem item5 = LineItem
                .builder()
                .title("Total Passed TCs")
                .value("****" + ResultListener.passedtests.size() + "****").build();
        
        LineItem item6 = LineItem
                .builder()
                .title("Total Failed TCs")
                .value("****" + ResultListener.failedtests.size() + "****").build();
        
        LineItem item7 = LineItem
                .builder()
                .title("Total Skipped TCs")
                .value("****" + ResultListener.skippedtests.size() + "****").build();

        LineItem item8 = LineItem
                .builder()
                .title("Total Run Time")
                .value("****" + ResultListener.executionTime() + " ms" + "****").build();

        return  BodyItem.builder()
                .type("FactSet")
                .isVisible(true)
                .spacing("Large")
                .facts(Arrays.asList(item1,item2,item3,item4,item5,item6,item7,item8))
                .build();
    }

    public BodyItem testInfoHeaders(){
        ItemsItem itemsItem1 = ItemsItem.builder()
                .type("TextBlock")
                .text("TC ID")
                .weight("Bolder")
                .isVisible(true)
                .wrap(true)
                .build();

        ItemsItem itemsItem2 = ItemsItem.builder()
                .type("TextBlock")
                .text("TC Name")
                .weight("Bolder")
                .isVisible(true)
                .wrap(true)
                .build();
        
        ItemsItem itemsItem3 = ItemsItem.builder()
                .type("TextBlock")
                .text("TC Description")
                .weight("Bolder")
                .isVisible(true)
                .wrap(true)
                .build();

        ItemsItem itemsItem4 = ItemsItem.builder()
                .type("TextBlock")
                .text("Result")
                .weight("Bolder")
                .color("dark")
                .isVisible(true)
                .wrap(true)
                .build();

        ColumnsItem columnsItem1 = ColumnsItem.builder()
                .type("Column")
                .items(Arrays.asList(itemsItem1))
                .width("auto").build();

        ColumnsItem columnsItem2 = ColumnsItem.builder()
                .type("Column")
                .spacing("medium")
                .items(Arrays.asList(itemsItem2))
                .width("stretch").build();

        ColumnsItem columnsItem3 = ColumnsItem.builder()
                .type("Column")
                .items(Arrays.asList(itemsItem3))
                .width("auto").build();
        
        ColumnsItem columnsItem4 = ColumnsItem.builder()
                .type("Column")
                .items(Arrays.asList(itemsItem4))
                .width("auto").build();

        ItemsItem itemsItem = ItemsItem.builder()
                .type("ColumnSet")
                .isVisible(false) //set to True if you want to publish test case data
                .columns(Arrays.asList(columnsItem1,columnsItem2,columnsItem3,columnsItem4))
                .build();

        return  BodyItem.builder()
                .type("Container")
                .isVisible(true)
                .items(Arrays.asList(itemsItem))
                .build();
    }

    public BodyItem testCaseData(){

    	ColumnsItem columnsItem1 = ColumnsItem.builder()
                .type("Column")
                .items(TestCaseID)
                .width("auto").build();

        ColumnsItem columnsItem2 = ColumnsItem.builder()
                .type("Column")
                .spacing("medium")
                .items(TestCaseName)
                .width("stretch").build();

        ColumnsItem columnsItem3 = ColumnsItem.builder()
                .type("Column")
                .spacing("medium")
                .items(TestCaseDesc)
                .width("auto").build();
        
        ColumnsItem columnsItem4 = ColumnsItem.builder()
                .type("Column")
                .spacing("medium")
                .items(TestResult)
                .width("auto").build();


        ItemsItem itemsItem = ItemsItem.builder()
                .type("ColumnSet")
                .isVisible(true)
                .columns(Arrays.asList(columnsItem1,columnsItem2,columnsItem3,columnsItem4)).build();

        return BodyItem.builder().type("Container")
        		.isVisible(false) //set to True if you want to publish test case data
                .items(Arrays.asList(itemsItem)).build();
    }

    public BodyItem additionalStuff(){
        ActionsItem actionsItem1 = ActionsItem.builder()
                .title("Click to view detailed test report")
                .type("Action.OpenUrl")
                .url("https://www.atlassian.com/software/jira")
                .style("destructive")
                .build();

//        ActionsItem actionsItem2 = ActionsItem.builder()
//                .title("Raise Bug")
//                .type("Action.OpenUrl")
//                .url("https://www.atlassian.com/software/jira")
//                .style("destructive")
//                .build();

        ItemsItem itemsItem = ItemsItem.builder()
                .type("ActionSet")
                .isVisible(true)
                .actions(Arrays.asList(actionsItem1)).build();

        return  BodyItem.builder()
                .type("Container")
                .isVisible(true)
                .items(Arrays.asList(itemsItem))
                .build();
    }
}
