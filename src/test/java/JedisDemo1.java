import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @auther: huzh
 * @date: 2019/1/21
 * jedis的测试
 */
public class JedisDemo1 {
    @Test
    /*
     *单实例的测试
     */
    public void demo1(){
        //1.设置ip地址和端口（require lunix system to open the 6379 port in iptables）
        /*
        * my machine doesn`t install the iptables ,so i need to install the iptables
        * first stop your firewall service
        *   systemctl stop firewalld
            systemctl mask firewalld
        * second install service about iptables
            yum install -y iptables
            yum install iptables-services
        * next do some config
        * last open the iptables service
        *   systemctl start iptables.service
        * restart firewall to let it go into effect
        *   systemctl restart iptables.service
        * to let it boot self starting
        *   systemctl enable iptables.service
        * */
        Jedis jedis = new Jedis("192.168.150.128",6379);
        //2、保存数据
        jedis.set("name1","huzh");
        //3.获取并打印数据
        String value = jedis.get("name1");
        System.out.println(value);
        //4.release the resource
        jedis.close();
    }
    /*
     * use connection pool
     */
    @Test
    public void demo2(){
        JedisPoolConfig config = new JedisPoolConfig();
        //set maximum connection number
        config.setMaxTotal(30);
        //set maximum free connection number
        config.setMaxIdle(10);
        JedisPool pool = new JedisPool(config,"192.168.150.128",6379);
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set("name2","jiangshul");
            String value = jedis.get("name2");
            System.out.println(value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null) jedis.close();
            if(pool!=null) pool.close();
        }
    }
}
