package com.example.springboot.user;

public final class Association {

        private Integer _randomCode;
        //private byte[] _sharedSecret;

        public Association(Integer randomCode) {
            this._randomCode = randomCode;
        }

        public Integer getRandomCode() {
            return this._randomCode;
        }

       /* public byte[] getSharedSecret() {
            return this._sharedSecret;
        }*/

        public void setRandomCode(Integer randomCode) {
            this._randomCode = randomCode;
        }

        /*public void setSharedSecret(byte[] sharedSecret) {
            this._sharedSecret = sharedSecret;
        }*/



}
