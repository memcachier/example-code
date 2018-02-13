# Run this examples

Change to application dir:

```
$ cd spymemcached <OR> xmemcached
```

Make sure the following ENV variables are set in a `.env` file:

```
MEMCACHIER_USERNAME=
MEMCACHIER_PASSWORD=
MEMCACHIER_SERVERS=
```

Then run:

```
$ mvn compile
$ mvn package
$ foreman start
```
