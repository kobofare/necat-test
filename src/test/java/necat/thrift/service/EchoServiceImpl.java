package necat.thrift.service;

import org.apache.thrift.TException;

import necat.thrift.echo.EchoService;

public class EchoServiceImpl implements EchoService.Iface {

    @Override
    public String echoString(String para) throws TException {
        return para;
    }

    @Override
    public int echoInt(int para) throws TException {
        return para;
    }

    @Override
    public boolean echoBoolean(boolean para) throws TException {
        return para;
    }

    @Override
    public void echoVoid() throws TException {
    }

    @Override
    public String echoNull() throws TException {
        return null;
    }

}
