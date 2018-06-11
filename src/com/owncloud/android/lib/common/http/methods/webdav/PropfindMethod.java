/* ownCloud Android Library is available under MIT license
 *   Copyright (C) 2018 ownCloud GmbH.
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *   EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *   MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *   BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *   ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *   CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 */

package com.owncloud.android.lib.common.http.methods.webdav;

import java.io.IOException;
import java.util.Set;

import at.bitfire.dav4android.DavResource;
import at.bitfire.dav4android.PropertyUtils;
import at.bitfire.dav4android.exception.DavException;
import at.bitfire.dav4android.exception.HttpException;
import at.bitfire.dav4android.exception.UnauthorizedException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Propfind calls wrapper
 * @author David González Verdugo
 */
public class PropfindMethod extends DavMethod {

    private int mDepth;
    private Set<DavResource> mMembers;

    public PropfindMethod(OkHttpClient okHttpClient, HttpUrl httpUrl, int depth) {
        super(okHttpClient, httpUrl);
        mDepth = depth;
    };

    @Override
    public int execute() throws IOException, HttpException, DavException {
        try {
            mDavResource.propfind(mDepth, PropertyUtils.INSTANCE.getAllPropSet());
            mMembers = mDavResource.getMembers();
        } catch (UnauthorizedException davException) {
            // Do nothing, we will use the 401 code to handle the situation
        }

        mRequest = mDavResource.getRequest();
        mResponse = mDavResource.getResponse();

        return super.getStatusCode();
    }

    public int getDepth() {
        return mDepth;
    }

    public Set<DavResource> getMembers() {
        return mMembers;
    }
}