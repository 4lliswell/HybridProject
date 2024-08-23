package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class ProductDetailsPage {

    public ProductDetailsPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h1[@class='o-productDetail__title']")
    public WebElement productDetail;

    @FindBy(id = "priceNew")
    public WebElement productPrice;

    @FindBy(xpath = "//span[@class='m-variation__item']")
    public List<WebElement> productSizeList;

    @FindBy(id = "addBasket")
    public WebElement addBasketButton;

    @FindBy(xpath = "//a[@title='Sepetim']")
    public WebElement myBasket;


}
