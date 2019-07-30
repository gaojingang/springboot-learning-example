协议格式：
HEADcontent-length:xxxHEADBODYxxxxxxBODY

HEADcontent-length:xxxHEAD  获取到 contentlength 的长度，然后再判断 body中的内容长度是否与之一致，一致再处理，否则抛弃。
