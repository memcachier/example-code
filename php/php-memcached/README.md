# Requirements

Make sure `ext-memcached` is installed:
```
$ composer show --platform
...
ext-memcached       3.0.4    The memcached PHP extension
...
```

Else install:
```
sudo pacman -S php-memcached
```
Edit this file
```
$ vim /etc/php/conf.d/memcached.ini
```
and uncomment
```
;extension=memcached.so
```
to
```
extension=memcached.so
```

Start project:
```
$ composer init
$ composer require ext-memcached
$ composer update
```
