package se.david.labs.producer;

class Foo {
    private String foo;
    private String bar;
    private String baz;

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
