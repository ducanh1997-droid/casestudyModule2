import service.impl.AccountManager;
import service.impl.CarManager;
import service.impl.DriverManager;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountManager accountManager = new AccountManager();
        DriverManager driverManager = new DriverManager();
        CarManager carManager = new CarManager(driverManager);
        menuAccount(carManager,accountManager,driverManager,scanner);
    }
    private static void menuAccount(CarManager carManager,AccountManager accountManager,DriverManager driverManager, Scanner scanner) {
        int choice;
        do {
            System.out.println("1. Đăng ký tài khoản");
            System.out.println("2. Đăng nhập");
            System.out.println("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    accountManager.create(scanner);
                    break;
                case 2:
                    if (accountManager.login(accountManager.getAccounts(), scanner)) {
                        menuCarManager(carManager,accountManager,driverManager,scanner);
                    }
            }
        }while (choice != 0) ;
    }
    private static void menuCarManager(CarManager carManager,AccountManager accountManager,DriverManager driverManager,Scanner scanner){
        int choice;
        do {
            System.out.println("Hệ thống quản lý xe taxi:");
            System.out.println("1. Tìm kiếm danh sách xe theo tên");
            System.out.println("2. Cập nhật thông tin tài xế theo xe");
            System.out.println("3. Xoá 1 xe");
            System.out.println("4. Thêm 1 xe");
            System.out.println("5. Hiển thị danh sách xe");
            System.out.println("6. Quản lý danh sách tài xế");
            System.out.println("7. Đăng xuất");
            System.out.println("8. Sửa thông tin cá nhân");
            System.out.println("9. Đổi mật khẩu");
            if(accountManager.currentLogin().getRole().equals("admin")){
                System.out.println("10. Quản lý user");
            }

            System.out.println("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    carManager.findCarByName(scanner);
                    break;
                case 2:
                    carManager.updateDriverWithCar(scanner);
                    break;
                case 3:
                    carManager.deleteById(scanner);
                    break;
                case 4:
                    carManager.create(scanner);
                    break;
                case 5:
                    carManager.displayAll();
                    break;
                case 6:
                    driverManager(driverManager,scanner);
                    break;
                case 7:
                    accountManager.logout();
                    menuAccount(carManager,accountManager,driverManager,scanner);
                    break;
                case 10:
                    if(accountManager.currentLogin().getRole().equals("admin")){
                        accountManager.displayAll();
                        userManager(accountManager,scanner);
                    }
                    break;
                case 8:
                    accountManager.changeInfoUser(scanner);
                    break;
                case 9:
                    accountManager.changePassword(scanner);
                    break;
            }
        } while (choice != 0);
    }
    private static void driverManager(DriverManager driverManager,Scanner scanner){
        int choice;
        do {
            System.out.println("1.Thêm 1 tài xế");
            System.out.println("2.Hiển thị danh sách tài xế");
            System.out.println("3.Xoá tài xế theo id");
            System.out.println("4.Sửa thông tin tài xế theo id");
            System.out.println("0.Exit");
            System.out.println("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    driverManager.create(scanner);
                    break;
                case 2:
                    driverManager.displayAll();
                    break;
                case 3:
                    driverManager.deleteById(scanner);
                    break;
                case 4:
                    driverManager.editInfoDriver(scanner);
                    break;
            }
        } while (choice != 0);
    }
    private static void userManager(AccountManager accountManager,Scanner scanner){
        int choice;
        do {
            System.out.println("1.Phân quyền admin cho user");
            System.out.println("2.Xoá user trên hệ thống");
            System.out.println("0.Exit");
            System.out.println("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    accountManager.upAdminById(accountManager.getAccounts(),scanner);
                    break;
                case 2:
                    accountManager.deleteById(scanner);
                    break;
            }
        } while (choice != 0);
    }
}

