import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DBConnector {
	public Connection con;
	public Statement st;
	public ResultSet rs;
	public DBConnector() {
	try { 	
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("");
			
	}
	catch(Exception ex){System.out.println("Error:"+ex);

	}
	
	}
	public void login() {
	}
	String user = getParameter("textuser");//textbox variable
	String pw = getParameter("t1");//textbox variable
	try{
		String loginquery = "Select UserName, Password from User";
		rs = st.executeQuery(loginquery);
		while (rs.next())
		{
			String username = rs.getString(UserName);
			String password = rs.getString(Password);
			if(user.equals(username) && pw.equals(password))
			{
				//loginscreen
			}
			else{
				System.out.println("Invalid Username or Password");
			}
		}
	}catch(Exception ex){
		System.out.println("Error:"+ex);
	}
	private String getParameter(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	}
}
