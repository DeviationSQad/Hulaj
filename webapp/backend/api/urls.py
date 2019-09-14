from django.conf.urls import url, include

urlpatterns = [
    url(r'^rest-auth/', include('rest_auth.urls'))
]