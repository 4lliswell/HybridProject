package utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.time.Duration;


public class ReusableMethods {

    public static void moveToElementHover(WebElement login) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(login).perform();
    }

    public static void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(element)).click();
    }

    public static void clickElementByJS(WebElement element) {
        JavascriptExecutor jsexecutor = ((JavascriptExecutor) Driver.getDriver());
        jsexecutor.executeScript("arguments[0].click();", element);
    }

    public static String getCellData(int rowNum, int colNum) {
        Cell cell;
        String path = "src/test/resources/excelData.xlsx";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);

            Workbook workBook = WorkbookFactory.create(fileInputStream);
            Sheet workSheet = workBook.getSheet("Sayfa1");
            cell = workSheet.getRow(rowNum).getCell(colNum);
            String cellData = cell.toString();
            return cellData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void wait(int sn){
        try {
            Thread.sleep(sn* 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
