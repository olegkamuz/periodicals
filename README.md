Diagram below shows us how we can using only Servlet - keep persistence of magazines, sort, filter and number of pages between different app pages.
![alt text](https://github.com/olegkamuz/periodicals/blob/main/keeping_magazines_sort_filter_page_selection.png?raw=true)

Diagram of database structure (ER model).
![alt text](https://github.com/olegkamuz/periodicals/blob/main/sql/modelPeriodical.png?raw=true)

Functional features:
- preserve selection(checked magazines, sort, filter, page):
    - for guest
    - for guest to client
    - for client
      until subscriptions purchased or reset checked magazines by button
- search with sort, filter, page
- fully functional pagination (for any amount of pages) using
  simple paging when less than 7 and carriage style with three dots on every side othewise
- validation using pattern 'chain of responsibility'
- separate exceptions on each layer(command, repository, dao)
- errors on same page when:
    - required field is empty
    - login, first name, last name not in range of letters, numbers, underscore, hyphen and spaces as internal separator
- xss attack prevention
- html escaped string
- blocked user can't log in
