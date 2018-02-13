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
          AddrUtil.getAddresses(System.getenv("MEMCACHIER_SERVERS"));
        AuthInfo authInfo =
          AuthInfo.plain(System.getenv("MEMCACHIER_USERNAME"),
                         System.getenv("MEMCACHIER_PASSWORD"));

        MemcachedClientBuilder builder =
          new XMemcachedClientBuilder(servers);

        // configure SASL auth for each server
        for(InetSocketAddress server : servers) {
          builder.addAuthInfo(server, authInfo);
        }

        // use binary protocol
        builder.setCommandFactory(new BinaryCommandFactory());

        // builder.setFailureMode(false);

        try {
          MemcachedClient mc = builder.build();

          try {
            mc.set("user", 0, "pass");
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
