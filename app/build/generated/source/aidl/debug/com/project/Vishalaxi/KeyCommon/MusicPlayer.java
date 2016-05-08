/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\U.I.C\\ACADEMICS\\Spring 2016\\CS478\\Submitted\\Project4\\AudioServer\\app\\src\\main\\aidl\\com\\project\\Vishalaxi\\KeyCommon\\MusicPlayer.aidl
 */
package com.project.Vishalaxi.KeyCommon;
public interface MusicPlayer extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.project.Vishalaxi.KeyCommon.MusicPlayer
{
private static final java.lang.String DESCRIPTOR = "com.project.Vishalaxi.KeyCommon.MusicPlayer";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.project.Vishalaxi.KeyCommon.MusicPlayer interface,
 * generating a proxy if needed.
 */
public static com.project.Vishalaxi.KeyCommon.MusicPlayer asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.project.Vishalaxi.KeyCommon.MusicPlayer))) {
return ((com.project.Vishalaxi.KeyCommon.MusicPlayer)iin);
}
return new com.project.Vishalaxi.KeyCommon.MusicPlayer.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_playSong:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.playSong(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_resume_or_Play_Song:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.resume_or_Play_Song(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_stopSong:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.stopSong(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_pauseSong:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.pauseSong(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getTransactions:
{
data.enforceInterface(DESCRIPTOR);
java.util.List _result = this.getTransactions();
reply.writeNoException();
reply.writeList(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.project.Vishalaxi.KeyCommon.MusicPlayer
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void playSong(int songNumber) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(songNumber);
mRemote.transact(Stub.TRANSACTION_playSong, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void resume_or_Play_Song(int songNumber) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(songNumber);
mRemote.transact(Stub.TRANSACTION_resume_or_Play_Song, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stopSong(int songNumber) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(songNumber);
mRemote.transact(Stub.TRANSACTION_stopSong, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void pauseSong(int songNumber) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(songNumber);
mRemote.transact(Stub.TRANSACTION_pauseSong, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.util.List getTransactions() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getTransactions, _data, _reply, 0);
_reply.readException();
java.lang.ClassLoader cl = (java.lang.ClassLoader)this.getClass().getClassLoader();
_result = _reply.readArrayList(cl);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_playSong = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_resume_or_Play_Song = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_stopSong = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_pauseSong = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getTransactions = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void playSong(int songNumber) throws android.os.RemoteException;
public void resume_or_Play_Song(int songNumber) throws android.os.RemoteException;
public void stopSong(int songNumber) throws android.os.RemoteException;
public void pauseSong(int songNumber) throws android.os.RemoteException;
public java.util.List getTransactions() throws android.os.RemoteException;
}
