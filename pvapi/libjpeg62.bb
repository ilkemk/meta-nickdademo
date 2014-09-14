DESCRIPTION = "libjpeg62"
LICENSE = "CLOSED"
DEPENDS = "jpeg"
PR = "r0"

PACKAGES = "${PN}"

do_install () {
	install -d ${D}/${libdir}

	# Required symbolic link: libjpeg.so.62->libjpeg.so.8.4.0
	ln -sf libjpeg.so.8.4.0 ${D}${libdir}/libjpeg.so.62
}

RPROVIDES_${PN} += "libjpeg.so.62()(64bit)"

FILES_${PN} += "${libdir}/libjpeg.so.62"
