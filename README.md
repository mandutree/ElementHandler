# ElementHandler
ElementHandler is an example code that demonstrates how working with WebElement can be simplified.  [ElementHandler](https://github.com/mandutree/ElementHandler/blob/master/src/main/java/com/appium/ElementHandler.java)
is meant to be extended by a **page object** representing the tested application or a website.  Alternatively, the
**components** within a page is meant to extend the [Component](https://github.com/mandutree/ElementHandler/blob/master/src/main/java/com/appium/Component.java).

## Page Object
An example of a page object can bee seen on the [WebHomePage](https://github.com/mandutree/AcmePages/blob/master/src/main/java/com/applitools/demo/page/web/WebHomePage.java).
This page object represents the [ACME Demo website's](https://demo.applitools.com/app.html) home page.  It extends
the [Page](https://github.com/mandutree/ElementHandler/blob/master/src/main/java/com/appium/Page.java), which in turn
extends the [ElementHandler](https://github.com/mandutree/ElementHandler/blob/master/src/main/java/com/appium/ElementHandler.java).
The WebHomePage can interact with objects within that page by calling method in ElementHandler.
eg. **webHomePage.clickAddAccount()**.

The ElementHandler allows two types of interactions.  The one that waits for the element to appear, and the one that
does not wait.  **eg: waitToClick() vs click()**.  Both types ultimately converges into the same waitForCondition()
method with either Duration.ZERO or non zero Duration.

## Component
Some components within a page is too big to be handled directly by the page objects.  And some components are reused
in different parts of the application.  In these cases, it's preferable to create an independent component objects
representing that component. It is fair to note that a component can also contain other components.

In the [ACME Demo website](https://demo.applitools.com/app.html) a *Recent Transaction* section contains a table of
repeating transaction rows.  Looking at the [WebTransactions](https://github.com/mandutree/AcmePages/blob/master/src/main/java/com/applitools/demo/component/web/WebTransactions.java)
you can see that there is a title and a grid.  Expanding further into the [WebGrid](https://github.com/mandutree/AcmePages/blob/master/src/main/java/com/applitools/demo/component/web/WebGrid.java)
you can see methods that will find you the GridRow of your interest via index, or by description.  It also contains
methods pertaining to the grid, such as getting row count.  Looking further into the [WebGridRow](https://github.com/mandutree/AcmePages/blob/master/src/main/java/com/applitools/demo/component/web/WebGridRow.java)
you can see methods to get specific values of the row.

In short, components contains mixture of methods that will perform actions (or get data) and methods that will
construct smaller components within that component.

### Parent Child Relationship
Contrast to Page Objects, Components have parent child relationship.  This means that a component must be within a page
or some other component.  In most cases, this is also true with the DOM tree that represents the elements.  We can
capitalize on this structure to simplify the way we interact with the object.  Consider the following HTML.

    <thead class="header">
        <tr> <td class="company">Company</td>    <td class="category">Category</td>   <td class="amount">Amount</td> </tr>
    </thead>
    <tbody class="data">
        <tr> <td class="company">Starbucks</td>  <td class="category">Cafe</td>       <td class="amount">1250</td> </tr>
        <tr> <td class="company">Ebay</td>       <td class="category">Ecommerce</td>  <td class="amount">321</td> </tr>
        <tr> <td class="company">Amazon</td>     <td class="category">Ecommerce</td>  <td class="amount">425</td> </tr>
    </tbody>

Imagine that we want to construct WebGridRow representing the Ebay row.  The implementation of getRowByCompany method
might look something like this:

    WebGridRow getRowByCompany(String xPath) {
        return new WebGridRow(String.format("//tr[td[@class='company'][text()='%s']]", companyName));
    }

We would call the method like this:

    getRowByCompany("Ebay");

In this case, the newly constructed WebGridRow would hold on to xPathToSelf which would have value like this:

    xPathToSelf = "//tr[td[@class='company'][text()='Ebay']]"

In the subsequent call within WebGridRow; To get the value for company, category, and amount, the method would look
something like this:

    String company() { return getText(xPathToSelf + "/td[@class='company']") }
    String category() { return getText(xPathToSelf + "/td[@class='category']") }
    String amount() { return getText(xPathToSelf + "/td[@class='amount']") }

This means that the WebGridRow has no control over what constitutes as self.  This means that you can identify the row
in which every way you choose.  eg, by row index, or select by company name.  You could even construct based on
combination of index and category (eg, first Ecommerce in the list).  The job of identifying "which row" is now outside
the responsibility of the WebGridRow. All that the WebGridRow needs to be concern is how to find the specific item
within that one row.

Taking all this into account your test may look something like this:

    GridRow ebay = catalog.homePage().recentTransactions().grid().rowByDescription("Ebay");
    Assert.assertEquals(ebay.company(), "Ebay");
    Assert.assertEquals(ebay.category(), "Ecommerce");
    Assert.assertEquals(ebay.amount(), "321");
