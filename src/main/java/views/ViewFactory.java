package views;

import java.io.IOException;
import java.net.URL;

import controllers.admin.AdminController;
import controllers.client.AccountsController;
import controllers.client.ClientController;
import controllers.client.ProfileController;
import controllers.client.TransactionController;
import controllers.dashboard.DashBoardController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {

	private AccountType loginType;
	// client view
	private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
	private AnchorPane root;
	private AnchorPane transactionview;
	private AnchorPane accountsview;

	// admin views
	private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
	private AnchorPane createClientview;
	private AnchorPane ClientView;
	private AnchorPane DepositView;
	private AnchorPane WithdrawView;
	private AnchorPane AddAccountType;
	private AnchorPane profile;


	public ViewFactory() {
		this.loginType = AccountType.CLIENT;
		this.clientSelectedMenuItem = new SimpleObjectProperty<>();
		this.adminSelectedMenuItem = new SimpleObjectProperty<>();
	}

	/**
	 * @return the loginType
	 */
	public AccountType getLoginType() {
		return loginType;
	}

	/**
	 * @param loginType the loginType to set
	 */
	public void setLoginType(AccountType loginType) {
		this.loginType = loginType;
	}

	/*
	 * client view section / /**
	 * 
	 * @return the clientSelectedMenuItem
	 */
	public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
		return clientSelectedMenuItem;
	}

	/**
	 * @return the accountsview
	 */
	public AnchorPane getAccountsview(String payeeAddress) {
		if (accountsview == null) 
				
				 try {
					  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/Accounts.fxml"));
					  accountsview = loader.load();
					  AccountsController Controller = loader.getController();
			          
			          if (payeeAddress != null) {
			        	  Controller.setPayeeAddress(payeeAddress);
			          } 
				  } catch (IOException e) {
					  e.printStackTrace(); 
				  
				  }
			        
		return accountsview;
	}

	/**
	 * @return the dashboardview
	 */	
	  public AnchorPane getDashboardView(String payeeAddress) {
         if(root == null) {
		  try {
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard/DashBoard.fxml"));
          root = loader.load();

          // Get the controller from the loader
          DashBoardController dashboardController = loader.getController();
          
          // Set the payeeAddress to the dashboard controller
          if (payeeAddress != null) {
        	  dashboardController.setPayeeAddress(payeeAddress);
              
          } else {
              System.out.println("Payee address is null!");
          }
	
	  } catch (IOException e) {
		  e.printStackTrace(); 
	  
	  }
         }
		return root; }
	 
	  /**
		 * @return the profile
		 */
		public AnchorPane getProfile(String payeeAddress) {

			if (profile == null) {
				 try {
					  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/ClientProfile.fxml"));
					  profile = loader.load();
					  ProfileController Controller = loader.getController();
			          
			          if (payeeAddress != null) {
			        	  Controller.setPayeeAddress(payeeAddress);
			          } 
				  } catch (IOException e) {
					  e.printStackTrace(); 
				  
				  }
		}
			return profile; 
		}

	/**
	 * @return the transactionview
	 */
	public AnchorPane getTransactionview(String payeeAddress) {
		if (transactionview == null) {
				 try {
					  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/Transaction.fxml"));
					  transactionview = loader.load();
					  TransactionController Controller = loader.getController();
			          
			          if (payeeAddress != null) {
			        	  Controller.setPayeeAddress(payeeAddress);
			          } 
				  } catch (IOException e) {
					  e.printStackTrace(); 
				  
				  }
		}
		return transactionview;
	}
	

	  public void showClientWindow(String payee) { 
		  try { 
	  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/Client.fxml"));
	  ClientController clientController = new ClientController();
	  loader.setController(clientController);
	  clientController.setPayeeAddress(payee);
	  createStage(loader);
	 
	  } catch (Exception e) { e.printStackTrace(); } }
	 

	/*
	 * Admin views section
	 */

	/**
	 * @return the adminSelectedMenuItem
	 */
	public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
		return adminSelectedMenuItem;
	}

	/**
	 * @return the addAccountType
	 */
	public AnchorPane getAddAccountType() {
		if (AddAccountType == null) {
			try {
				AddAccountType = new FXMLLoader(getClass().getResource("/fxml/admin/AddClientAccountType.fxml")).load();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return AddAccountType;
	}
	/**
	 * @return the withdrawView
	 */
	public AnchorPane getWithdrawView() {
		if (WithdrawView == null) {
			try {
				WithdrawView = new FXMLLoader(getClass().getResource("/fxml/admin/Withdraw.fxml")).load();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return WithdrawView;
	}

	/**
	 * @return the depositView
	 */
	public AnchorPane getDepositView() {
		if (DepositView == null) {
			try {
				DepositView = new FXMLLoader(getClass().getResource("/fxml/admin/Deposit.fxml")).load();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return DepositView;
	}

	/**
	 * @return the createClientview
	 */
	public AnchorPane getCreateClientview() {
		if (createClientview == null) {
			try {
				createClientview = new FXMLLoader(getClass().getResource("/fxml/admin/CreateClient.fxml")).load();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return createClientview;
	}

	/**
	 * @return the clientView
	 */
	public AnchorPane getClientView() {
		if (ClientView == null) {
			try {
				ClientView = new FXMLLoader(getClass().getResource("/fxml/admin/ViewClients.fxml")).load();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ClientView;
	}

	public void showAdminWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/Admin.fxml"));
			AdminController admincontroller = new AdminController();
			loader.setController(admincontroller);
			createStage(loader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//
	private void createStage(FXMLLoader loader) {
		Scene scene = null;
		try {
			scene = new Scene((Parent) loader.load());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/bank.png"))));
		stage.setResizable(false);
		stage.setTitle("Nana Bank");
		stage.show();
	}

	public void closeStage(Stage stage) {
		stage.close();
	}

	public void showLoginWindow() {
		try {
			// Load the FXML file
			URL fxmlUrl = getClass().getResource("/fxml/login.fxml");
			System.out.println("FXML URL: " + fxmlUrl); // Check if the URL is correct

			if (fxmlUrl == null) {
				throw new IllegalArgumentException("FXML file not found!");
			}

			FXMLLoader loader = new FXMLLoader(fxmlUrl);
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);

			// Load the icon image
			Image iconImage = new Image(getClass().getResourceAsStream("/images/bank.png"));
			stage.getIcons().add(iconImage);

			stage.setResizable(false);
			stage.setTitle("Nana Bank");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
