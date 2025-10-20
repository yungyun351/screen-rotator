package com.marttapps.screenrotator.service;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface WinKernel32Service extends Library {

	final WinKernel32Service INSTANCE = Native.load("kernel32", WinKernel32Service.class);

	int GetModuleFileNameW(Pointer hModule, char[] lpFilename, int nSize);

}
