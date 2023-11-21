package com.neolians.website.repository.pages;
//  ajouter par Mohsen : PagePresentation
import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PagePresentation {
    private PagePresentation()

    {throw new IllegalStateException("Utility class");}
    public static final UrlElement url = new UrlElement("Home url", "/presentation/");
    //  public static final BlockElement paragraphe1=new BlockElement("paragraphe1", By.xpath("//div[@data-id=\"5f19968\"]"));
    public static final BlockElement paragraphe1=new BlockElement("paragraphe 1", By.xpath("//section[@data-id='5daee16']"));
    public static final BlockElement paragraphe2=new BlockElement("paragraphe 2", By.xpath("//div[@data-id=\"ece92cf\"]"));
    public static final BlockElement paragraphe3=new BlockElement("paragraphe 3", By.xpath("//section[@data-id=\"c9f1870\"]"));
    public static final BlockElement paragraphe4=new BlockElement("paragraphe 4", By.xpath("//section[@data-id=\"0095d07\"]"));

}
