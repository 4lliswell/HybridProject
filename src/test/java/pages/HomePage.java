package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class HomePage {

    public HomePage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "onetrust-accept-btn-handler")
    public WebElement cookiesAccept;

    @FindBy(xpath = "//button[@type='button']//*[name()='svg']")
    public WebElement closeButton;

    @FindBy(xpath = "//button[@class='dn-slide-deny-btn']")
    public WebElement allertCloseButton;

    @FindBy(xpath = "//input[@class='o-header__search--input']")
    public WebElement searchBox;

    @FindBy(id = "o-searchSuggestion__input")
    public WebElement searchBox1;

    @FindBy(xpath = "//button[@class='o-header__form--close']")
    public WebElement vazgecButton;


}
