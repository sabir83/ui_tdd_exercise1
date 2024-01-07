package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class TableHandler {

    public static List<WebElement> getTableRow(int rowNumber){
        return Driver.getDriver().findElements(By.cssSelector("tr:nth-child(" + rowNumber + ") td"));
    }

    public static List<WebElement> getTableColumn(int columnNumber){
        return Driver.getDriver().findElements(By.cssSelector("td:nth-child(" + columnNumber + ")"));
    }

    public static List<List<WebElement>> getTableData(WebElement table){
        // Create a new list of lists to hold table data
        // Each inner list represents one row of the table
        List<List<WebElement>> tableData = new ArrayList<>();
        // [ 1,	Amazon,	1,523,000,	USA ]
        // [ 2,	Alibaba, 245,700, China ]

        //Get all rows from the table at once (table rows) that are direct children of tbody
        List<WebElement> rows = table.findElements(By.cssSelector("tbody > tr"));
        // 1. 1	Amazon	1,523,000	USA    -- first element
        // 2. 2	Alibaba	245,700	China      -- second element
        // 3. 3	Microsoft	221,000	USA    -- third element
        // 4. 4	Apple	154,000	USA        -- fourth element
        // 5. 5	Samsung	116,915	S. Korea   -- fifth element

        for (WebElement row : rows) {
            tableData.add(row.findElements(By.tagName("td")));

        }
        return tableData;
    }
}
