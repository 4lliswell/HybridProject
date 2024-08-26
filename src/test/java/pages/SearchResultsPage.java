package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;


public class SearchResultsPage {

    public SearchResultsPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//div[@class='m-productImageList']")
    public List<WebElement> productList;

    @FindBy(id = "productListTitle")
    public WebElement resultProductTitle;
}
