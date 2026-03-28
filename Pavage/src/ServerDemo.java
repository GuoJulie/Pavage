import Network.*;
import Model.*;
import Controller.PaveCon;
import java.util.List;

public class ServerDemo {
    public static void main(String[] args) {
        System.out.println("=== Pavage Server Demo ===");
        System.out.println();
        
        // 1. 创建客户端连接
        System.out.println("1. 连接到服务器...");
        PavageClient client = new PavageClient("localhost", 12345);
        System.out.println("   ✓ 客户端创建成功");
        System.out.println();
        
        // 2. 创建测试数据
        System.out.println("2. 创建测试铺砖数据...");
        PaveM testPave = new PaveM();
        PaveCon paveCon = new PaveCon(testPave);
        System.out.println("   ✓ 测试数据创建成功");
        System.out.println("   - 顶点数量: " + testPave.getPointList().size());
        System.out.println("   - 边长: " + testPave.getLongueur_pave());
        System.out.println();
        
        // 3. 保存数据到服务器
        System.out.println("3. 保存数据到服务器...");
        PaveData data = DataConverter.toPaveData(testPave, "demo_user");
        String result = client.savePaveData(data);
        System.out.println("   " + result);
        System.out.println();
        
        // 4. 从服务器加载数据
        System.out.println("4. 从服务器加载数据...");
        List<PaveData> userData = client.loadUserData("demo_user");
        System.out.println("   ✓ 加载成功");
        System.out.println("   - 找到 " + userData.size() + " 条记录");
        System.out.println();
        
        // 5. 获取最新数据
        System.out.println("5. 获取最新数据...");
        PaveData latest = client.getLatestPaveData("demo_user");
        if (latest != null) {
            System.out.println("   ✓ 获取成功");
            System.out.println("   - 时间戳: " + latest.getTimestamp());
            System.out.println("   - 用户ID: " + latest.getUserId());
        } else {
            System.out.println("   ✗ 没有找到数据");
        }
        System.out.println();
        
        System.out.println("=== 演示完成 ===");
        System.out.println();
        System.out.println("服务器正在监听端口: 12345");
        System.out.println("GUI程序已连接并可以同步数据");
    }
}