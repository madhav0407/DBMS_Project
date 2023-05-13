# Instructions to run Project
1. Clone the GitHub repository onto your system via the terminal and using the following command:
```bash
git clone https://github.com/madhav0407/DBMS_Project.git
```

2. Open a new terminal and use the following command to open mysql. Upon entering the password, you will be directed into mysql.
```bash
mysql -u root -p
```

3. Now, create a database called bank and use it. This can be done using the following command:
```bash
create database bank;
use bank;
```

4. Use the sql source command to run the .sql files stored in the [sql directory](https://github.com/madhav0407/DBMS_Project/tree/master/sql). You will have to use the path to the .sql file when using the source command.
```bash
source <pathname_where_cloned>/DBMS_Project/sql/create.sql
source <pathname_where_cloned>/DBMS_Project/sql/alter.sql
source <pathname_where_cloned>/DBMS_Project/sql/insert.sql
```

5. Now, we will open the project folder into Visual Studio Code. Here, we will open file [DAO_Factory.java](https://github.com/madhav0407/DBMS_Project/blob/master/src/DAO_Factory.java). In this file, we should change the value of the variable PASS(on line 19) to your own sql password.

6. Open the [DAO_demo.java](https://github.com/madhav0407/DBMS_Project/blob/master/src/DAO_demo.java) (since this is the driver code) and now, we shall run the project by clicking the Run Java button(in the top right corner of the VS Code window) as shown in the figure below.
![Run Java](https://code.visualstudio.com/assets/docs/java/java-debugging/run-menu.png)

7. The Project will run in the terminal and is user driven.
