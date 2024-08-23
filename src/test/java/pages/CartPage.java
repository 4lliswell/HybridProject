package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class CartPage {

    public CartPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[@class='priceBox__salePrice']")
    public WebElement cartProductPrice;

    @FindBy(id = "quantitySelect0-key-0")
    public WebElement productQuantity;

    @FindBy(id = "removeCartItemBtn0-key-0")
    public WebElement productClearButton;

    @FindBy(xpath = "//div[@id='emtyCart']//*[@class='m-empty__messageTitle']")
    public WebElement emptyCartTitile;
}
