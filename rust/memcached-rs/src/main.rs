extern crate memcached;

use std::env;

use memcached::proto::{Operation, ProtoType};
use memcached::Client;

fn main() {
    let servers = env::var("MEMCACHIER_SERVERS").unwrap();
    let username = env::var("MEMCACHIER_USERNAME").unwrap();
    let password = env::var("MEMCACHIER_PASSWORD").unwrap();


    let mut client = Client::connect_sasl(servers.split(',').map(|s| format!("{}{}", "tcp://", s))
                                                            .map(|s| (s, 1))
                                                            .collect::<Vec<(String, usize)>>()
                                                            .as_slice(),
                                          ProtoType::Binary,
                                          &username, &password).unwrap();

    client.set(b"hello", b"MemCachier", 0xdeadbeef, 2).unwrap();
    let (value, flags) = client.get(b"hello").unwrap();


    println!("Got: {}", std::str::from_utf8(&value).unwrap());
    assert_eq!(&value[..], b"MemCachier");
    assert_eq!(flags, 0xdeadbeef);
}
