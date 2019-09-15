from rest_framework.serializers import ModelSerializer
from .models import User, UserProfile, Event, Post, Track, Scooter, Car
from rest_auth.models import TokenModel


class UserProfileSerializer(ModelSerializer):
    class Meta:
        model = UserProfile
        fields = ('id', 'photo', 'bio', 'date_of_birth', 'country', 'city', 'points')


class UserSerializer(ModelSerializer):
    profile = UserProfileSerializer(required=True)

    class Meta:
        model = User
        fields = ('id', 'email', 'first_name', 'last_name', 'password', 'profile')
        extra_kwargs = {'password': {'write_only': True}}

    def create(self, validated_data):
        profile_data = validated_data.pop('profile')
        password = validated_data.pop('password')
        user = User(**validated_data)
        user.set_password(password)
        user.save()
        UserProfile.objects.create(user=user, **profile_data)
        return user

    def update(self, instance, validated_data):
        profile_data = validated_data.pop('profile')
        profile = instance.profile

        instance.email = validated_data.get('email', instance.email)
        instance.save()

        profile.photo = profile_data.get('photo', profile.photo)
        profile.bio = profile_data.get('bio', profile.bio)
        profile.date_of_birth = profile_data.get('date_of_birth', profile.date_of_birth)
        profile.country = profile_data.get('country', profile.country)
        profile.city = profile_data.get('city', profile.city)
        profile.points = profile_data.get('points', profile.points)


class TokenSerializer(ModelSerializer):
    """
    Added user to login ourput in rest-auth
    """
    user = UserSerializer(many=False, read_only=True)  # prepare user

    class Meta:
        model = TokenModel
        fields = ('key', 'user')  # Return key and user


class TrackSerializer(ModelSerializer):
    class Meta:
        model = Track
        fields = ('id', 'id_user', 'time_start', 'time_end', 'duration', 'track_length')


class EventSerializer(ModelSerializer):
    class Meta:
        model = Event
        fields = ('id', 'id_user', 'title', 'description', 'place_name', 'country', 'city', 'address', 'event_date', 'max_amount_of_people', 'is_active')


class PostSerializer(ModelSerializer):
    class Meta:
        model = Post
        fields = ('id', 'id_user', 'post_type', 'title', 'text')


class ScooterSerializer(ModelSerializer):
    class Meta:
        model = Scooter
        fields = ('id', 'id_user' 'photo', 'mark', 'type', 'wheel_size', 'wheel_type', 'opinion')


class CarSerializer(ModelSerializer):
    class Meta:
        model = Car
        fields = ('id', 'id_user', 'mark', 'type', 'fuel_consumption_per_100', 'exhaust_amount')
