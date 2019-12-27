package se.david.labs.producer;

class Foo {
    private final String foo;
    private final String bar;
    private final String baz;

    Foo(String foo, String bar, String baz) {
        this.foo = foo;
        this.bar = bar;
        this.baz = baz;
    }

    public String getFoo() {
        return foo;
    }

    public String getBar() {
        return bar;
    }

    public String getBaz() {
        return baz;
    }
}
