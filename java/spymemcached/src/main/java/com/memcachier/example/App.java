package com.memcachier.example;

import java.io.IOException;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.auth.AuthDescriptor;

/**
 * Hello MemCachier!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },
            new PlainCallbackHandler(System.getenv("MEMCACHIER_USERNAME"),
                                     System.getenv("MEMCACHIER_PASSWORD")));
        try {
          MemcachedClient mc = new MemcachedClient(
              new ConnectionFactoryBuilder()
                  .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                  .setAuthDescriptor(ad).build(),
              AddrUtil.getAddresses(System.getenv("MEMCACHIER_SERVERS")));
          mc.set("foo", 0, "bar");
          System.out.println(mc.get("foo"));
          mc.shutdown();
        } catch (IOException ioe) {
          System.err.println("Couldn't create a connection to MemCachier: \n" +
                             "IOException " + ioe.getMessage());
        }
    }
}
