package com.memcachier.example;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.lang.InterruptedException;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeoutException;

/**
 * Hello MemCachier!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        List<InetSocketAddress> servers =
          AddrUtil.getAddresses(System.getenv("MEMCACHIER_SERVERS").replace(",", " "));
        AuthInfo authInfo =
          AuthInfo.plain(System.getenv("MEMCACHIER_USERNAME"),
                         System.getenv("MEMCACHIER_PASSWORD"));

        MemcachedClientBuilder builder = new XMemcachedClientBuilder(servers);

        // configure SASL auth for each server
        for(InetSocketAddress server : servers) {
          builder.addAuthInfo(server, authInfo);
        }

        // Use binary protocol (default: new TextCommandFactory())
        builder.setCommandFactory(new BinaryCommandFactory());
        // Failure mode is not failover. In failure mode failed server stay in list (default: false)
        builder.setFailureMode(false);
        // Connection timeout in milliseconds (default: )
        builder.setConnectTimeout(1000);
        // Reconnect to servers (default: true)
        builder.setEnableHealSession(true);
        // Delay until reconnect attempt in milliseconds (default: 2000)
        builder.setHealSessionInterval(2000);

        try {
          MemcachedClient mc = builder.build();

          try {
            mc.set("foo", 0, "bar");
            String val = mc.get("foo");
            System.out.println(val);
          } catch (TimeoutException te) {
            System.err.println("Timeout during set or get: " +
                               te.getMessage());
          } catch (InterruptedException ie) {
            System.err.println("Interrupt during set or get: " +
                               ie.getMessage());
          } catch (MemcachedException me) {
            System.err.println("Memcached error during get or set: " +
                               me.getMessage());
          }
          mc.shutdown();

        } catch (IOException ioe) {
          System.err.println("Couldn't create a connection to MemCachier: " +
                             ioe.getMessage());
        }

    }
}
